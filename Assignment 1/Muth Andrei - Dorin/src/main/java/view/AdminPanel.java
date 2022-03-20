package view;

import table.TableModel;
import view.date.DatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminPanel extends JPanel {

    private JButton viewEmployeesButton;
    private JButton addEmployeeButton;
    private JButton updateEmployeeButton;
    private JButton deleteEmployeeButton;
    private JButton viewEventsButton;
    private JButton filterButton;
    private JButton logoutButton;
    private JScrollPane employeesScrollPane = new JScrollPane();
    private JScrollPane eventsScrollPane = new JScrollPane();
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JTextField idTextField;
    private Date fromDate;
    private Date toDate;

    public AdminPanel() {
        setLayout(null);
        initializeButtons();
        initializeUserFields();
        addFields();
        addDatePicker();
    }

    private void initializeButtons() {
        viewEmployeesButton = new JButton("View employees");
        viewEmployeesButton.setBounds(10, 10, 130, 40);
        addEmployeeButton = new JButton("Add employee");
        addEmployeeButton.setBounds(10, 105, 120, 40);
        updateEmployeeButton = new JButton("Update employee");
        updateEmployeeButton.setBounds(10, 200, 140, 40);
        deleteEmployeeButton = new JButton("Delete employee");
        deleteEmployeeButton.setBounds(10, 245, 140, 40);
        viewEventsButton = new JButton("View events");
        viewEventsButton.setBounds(10, 290, 140, 40);
        filterButton = new JButton("Generate report");
        filterButton.setBounds(10, 410, 140, 40);
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(155, 410, 140, 40);
        add(viewEmployeesButton);
        add(addEmployeeButton);
        add(updateEmployeeButton);
        add(deleteEmployeeButton);
        add(viewEventsButton);
        add(filterButton);
        add(logoutButton);
    }

    public void addViewEmployeesButtonListener(ActionListener viewEmployeesButtonListener) {
        viewEmployeesButton.addActionListener(viewEmployeesButtonListener);
    }

    public void addAddEmployeeButtonListener(ActionListener addEmployeeButtonListener) {
        addEmployeeButton.addActionListener(addEmployeeButtonListener);
    }

    public void addUpdateEmployeeButtonListener(ActionListener updateEmployeeButtonListener) {
        updateEmployeeButton.addActionListener(updateEmployeeButtonListener);
    }

    public void addDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener) {
        deleteEmployeeButton.addActionListener(deleteEmployeeButtonListener);
    }

    public void addViewEventsButtonListener(ActionListener viewEventsButtonListener) {
        viewEventsButton.addActionListener(viewEventsButtonListener);
    }

    public void addFilterButtonListener(ActionListener filterButtonListener) {
        filterButton.addActionListener(filterButtonListener);
    }

    public void addLogoutButtonListener(ActionListener logoutButtonListener) {
        logoutButton.addActionListener(logoutButtonListener);
    }

    public void addEmployeesTable(TableModel tableModel) {
        employeesScrollPane.removeAll();
        remove(employeesScrollPane);
        JTable employeesTable = new JTable(tableModel);
        employeesScrollPane = new JScrollPane(employeesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        employeesScrollPane.setBounds(185, 10, 600, 100);
        add(employeesScrollPane);
        repaint();
        revalidate();
    }

    public void addEventsTable(TableModel tableModel) {
        eventsScrollPane.removeAll();
        remove(eventsScrollPane);
        JTable eventsTable = new JTable(tableModel);
        eventsScrollPane = new JScrollPane(eventsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        eventsScrollPane.setBounds(170, 250, 600, 100);
        add(eventsScrollPane);
        repaint();
        revalidate();
    }

    private void initializeUserFields() {
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(10, 55, 50, 20);
        emailTextField = new JTextField();
        emailTextField.setBounds(80, 55, 100, 20);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(10, 80, 80, 20);
        passwordField = new JPasswordField();
        passwordField.setBounds(80, 80, 100, 20);
        JLabel idLabel = new JLabel("User id to update/delete/generate report about:");
        idLabel.setBounds(10, 150, 300, 20);
        idTextField = new JTextField();
        idTextField.setBounds(10, 175, 40, 20);
        add(emailLabel);
        add(passLabel);
        add(idLabel);
    }

    private void addDatePicker() {
        JLabel dateLabel = new JLabel("Select two dates:");
        dateLabel.setBounds(10, 335, 100, 20);
        JButton date1 = new JButton("Date 1");
        date1.setBounds(10, 360, 80, 20);
        JTextField date1tf = new JTextField();
        date1tf.setBounds(10, 385, 80, 20);
        date1tf.setEditable(false);
        date1.addActionListener(e -> {
            DatePicker datePicker = new DatePicker();
            fromDate = datePicker.setPickedDate();
            if(fromDate != null) {
                date1tf.setText(new SimpleDateFormat("dd-MM-yyyy").format(fromDate));
            }
        });
        JButton date2 = new JButton("Date 2");
        date2.setBounds(95, 360, 80, 20);
        JTextField date2tf = new JTextField();
        date2tf.setBounds(95, 385, 80, 20);
        date2tf.setEditable(false);
        date2.addActionListener(e -> {
            DatePicker datePicker = new DatePicker();
            toDate = datePicker.setPickedDate();
            if(toDate != null) {
                date2tf.setText(new SimpleDateFormat("dd-MM-yyyy").format(toDate));
            }
        });
        add(dateLabel);
        add(date1);
        add(date2);
        add(date1tf);
        add(date2tf);
    }

    private void addFields() {
        add(emailTextField);
        add(passwordField);
        add(idTextField);
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

    public String getUserId() {
        return idTextField.getText();
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

}
