package controller;

import model.Event;
import model.Role;
import model.User;
import model.validator.UserValidator;
import repository.event.EventRepository;
import service.event.EventService;
import service.user.AuthenticationService;
import table.TableCreator;
import table.TableModel;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController {

    private final View view;
    private final AuthenticationService authenticationService;
    private TableCreator<User> usersTableCreator;
    private TableCreator<Event> eventsTableCreator;
    private final UserValidator userValidator;
    private final String UNEXPECTED_ERROR = "Something went wrong";
    private final EventService eventService;

    public AdminController(View view, AuthenticationService authenticationService, UserValidator userValidator, EventService eventService) {
        this.view = view;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;
        this.eventService = eventService;

        this.usersTableCreator = new TableCreator<>(User.class);
        this.eventsTableCreator = new TableCreator<>(Event.class);

        addButtonListeners();
    }

    private void addButtonListeners() {
        this.view.addViewEmployeesButtonListener(new ViewEmployeesButtonListener());
        this.view.addAddEmployeeButtonListener(new AddEmployeeButtonListener());
        this.view.addUpdateEmployeeButtonListener(new UpdateEmployeeButtonListener());
        this.view.addDeleteEmployeeButtonListener(new DeleteEmployeeButtonListener());
        this.view.addViewEventsButtonListener(new ViewEventsButtonListener());
        this.view.addFilterButtonListener(new FilterButtonListener());
        this.view.addAdminLogoutButtonListener(new AdminLogoutButtonListener());
    }

    private class ViewEmployeesButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<User> employees = authenticationService.findByRole(new Role(EMPLOYEE));
            TableModel tableModel = usersTableCreator.collectAllEntriesInTable(employees);
            view.addEmployeesTable(tableModel);
        }
    }

    private class AddEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailFromAdminPanel();
            String password = view.getPasswordFromAdminPanel();

            userValidator.validate(email, password);
            List<String> errors = userValidator.getErrors();
            final String message;
            if(errors.isEmpty()) {
                if(authenticationService.register(email, password)) {
                    message = "Employee added";
                }
                else {
                    message = UNEXPECTED_ERROR;
                }
            }
            else {
                message = userValidator.getFormattedErrors();
            }
            JOptionPane.showMessageDialog(view.getContentPane(), message);
        }
    }

    private void updateEmployee(Long id) {
        String email = view.getEmailFromAdminPanel();
        String password = view.getPasswordFromAdminPanel();

        userValidator.validateUpdate(email, password);
        List<String> errors = userValidator.getErrors();
        final String message;
        if(errors.isEmpty()) {
            if(authenticationService.update(id, email, password)) {
                message = "Employee updated successfully";
            }
            else {
                message = UNEXPECTED_ERROR;
            }
        }
        else {
            message = userValidator.getFormattedErrors();
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class UpdateEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getUserId();

            userValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            AdminController.this::updateEmployee,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), userValidator.getFormattedErrors())
                    );
        }
    }

    private void deleteEmployee(Long id) {
        final String message;
        if(authenticationService.remove(id)) {
            message = "Employee deleted";
        }
        else {
            message = UNEXPECTED_ERROR;
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class DeleteEmployeeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getUserId();

            userValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            AdminController.this::deleteEmployee,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), userValidator.getFormattedErrors())
                    );
        }
    }

    private class ViewEventsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Event> events = eventService.findAll();
            TableModel tableModel = eventsTableCreator.collectAllEntriesInTable(events);
            view.addEventsTable(tableModel);
        }
    }

    private void generateReport(Long id) {
        Date fromDate = view.getFromDate();
        Date toDate = view.getToDate();

        final String message;
        if(fromDate == null || toDate == null) {
            message = "Select both dates";
        } else {
            List<Event> events = eventService.filterByIdAndDate(id, fromDate, toDate);
            TableModel tableModel = eventsTableCreator.collectAllEntriesInTable(events);
            view.addEventsTable(tableModel);
            message = "Report generated";
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class FilterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getUserId();
            userValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            AdminController.this::generateReport,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), userValidator.getFormattedErrors())
                    );
        }
    }

    private class AdminLogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.changeToLoginPanel();
        }
    }

}
