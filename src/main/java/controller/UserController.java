package controller;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.TransferValidator;
import service.account.AccountService;
import service.client.ClientService;
import table.TableCreator;
import table.TableModel;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static database.Constants.AccountTypes.VISA;

public class UserController {

    private final View view;
    private final TableCreator<Client> clientsTableCreator;
    private final TableCreator<Account> accountsTableCreator;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ClientValidator clientValidator;
    private final AccountValidator accountValidator;
    private final TransferValidator transferValidator;
    private String selectedType = VISA;
    private final String UNEXPECTED_ERROR = "Something went wrong";

    public UserController(View view, ClientService clientService, AccountService accountService, ClientValidator clientValidator,
                          AccountValidator accountValidator, TransferValidator transferValidator) {
        this.view = view;
        this.clientService = clientService;
        this.accountService = accountService;
        this.clientValidator = clientValidator;
        this.accountValidator = accountValidator;
        this.transferValidator = transferValidator;

        this.clientsTableCreator = new TableCreator<>(Client.class);
        this.accountsTableCreator = new TableCreator<>(Account.class);

        addListenersToButtons();
    }

    private void addListenersToButtons() {
        this.view.addViewClientsButtonListener(new ViewClientsButtonListener());
        this.view.addAddButtonListener(new AddButtonListener());
        this.view.addUpdateClientsButtonListener(new UpdateClientButtonListener());
        this.view.addViewAccountsButtonListener(new ViewAccountsButtonListener());
        this.view.addAddAccountButtonListener(new AddAccountButtonListener());
        this.view.addTypeListener(new TypeListener());
        this.view.addUpdateAccountButtonListener(new UpdateAccountButtonListener());
        this.view.addTransferButtonListener(new TransferButtonActionListener());
        this.view.addDeleteAccountButtonListener(new DeleteAccountButtonListener());
        this.view.addProcessBillButtonListener(new ProcessBillButtonListener());
        this.view.addUserLogoutButtonListener(new UserLogoutButtonListener());
    }

    private class ViewClientsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Client> clients = clientService.findAll();
            TableModel tableModel = clientsTableCreator.collectAllEntriesInTable(clients);
            view.addClientsTable(tableModel);
        }
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = view.getName();
            String personalNumericalCode = view.getPersonalNumericalCode();
            String address = view.getAddress();

            clientValidator.validate(name, personalNumericalCode, address);
            List<String> errors = clientValidator.getErrors();
            final String message;
            if(errors.isEmpty()) {
                if(clientService.save(name, personalNumericalCode, address)) {
                    message = "Client added successfully";
                }
                else {
                    message = UNEXPECTED_ERROR;
                }
            }
            else {
                message = clientValidator.getFormattedErrors();
            }
            JOptionPane.showMessageDialog(view.getContentPane(), message);
        }
    }

    private void updateClient(Long id) {
        String name = view.getName();
        String personalNumericalCode = view.getPersonalNumericalCode();
        String address = view.getAddress();

        clientValidator.validateUpdate(name, personalNumericalCode, address);
        List<String> errors = clientValidator.getErrors();
        final String message;
        if(errors.isEmpty()) {
            if(clientService.update(id, name, personalNumericalCode, address)) {
                message = "Client updated successfully";
            }
            else {
                message = UNEXPECTED_ERROR;
            }
        }
        else {
            message = clientValidator.getFormattedErrors();
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private void updateAccount(Long id) {
        String cardNumber;
        cardNumber = view.getCardNumber();
        String clientIdString = view.getClientId();
        String moneyString = view.getMoney();
        String dayString = view.getDay();
        String monthString = view.getMonth();
        String yearString = view.getYear();

        accountValidator.getErrors().clear();
        if(!cardNumber.isBlank()) {
            accountValidator.validateCardNumberLength(cardNumber);
            accountValidator.validateCardNumber(cardNumber);
            accountValidator.validateCardNumberUniqueness(cardNumber);
        }
        if(!clientIdString.isBlank()) {
            accountValidator.validateClientId(clientIdString);
        }
        if(!moneyString.isBlank()) {
            accountValidator.validateMoney(moneyString);
        }
        accountValidator.validateDateUpdateExistence(dayString, monthString, yearString);
        List<String> errors = accountValidator.getErrors();
        final String message;
        if(errors.isEmpty()) {
            boolean success = true;
            if(!dayString.isBlank() && !monthString.isBlank() && !yearString.isBlank()) {
                int day = Integer.parseInt(dayString);
                int month = Integer.parseInt(monthString);
                int year = Integer.parseInt(yearString);
                Date date = createDate(day, month, year);
                success = accountService.updateDateField(id, date);
            }
            if(success && accountService.updateStringFields(id, cardNumber, selectedType) &&
                    accountService.updateLongFields(id, clientIdString, moneyString)) {
                message = "Account updated successfully";
            } else {
                message = UNEXPECTED_ERROR;
            }
        } else {
            message = accountValidator.getFormattedErrors();
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class UpdateClientButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getId();

            clientValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            UserController.this::updateClient,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), clientValidator.getFormattedErrors())
                    );
        }
    }

    private class UpdateAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getAccountId();

            accountValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            UserController.this::updateAccount,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), accountValidator.getFormattedErrors())
                    );
        }
    }

    private class ViewAccountsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Account> accounts = accountService.findAll();
            TableModel tableModel = accountsTableCreator.collectAllEntriesInTable(accounts);
            view.addAccountsTable(tableModel);
        }
    }

    private class TypeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JComboBox comboBox = (JComboBox) e.getSource();
            selectedType = (String) comboBox.getSelectedItem();
        }
    }

    private Date createDate(int day, int month, int year) {
        LocalDate localDate = LocalDate.of(year, month, day);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    private class AddAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cardNumber = view.getCardNumber();
            String idString = view.getClientId();
            String moneyString = view.getMoney();
            String dayString = view.getDay();
            String monthString = view.getMonth();
            String yearString = view.getYear();

            accountValidator.getErrors().clear();
            accountValidator.validateNonDateFields(cardNumber, idString, moneyString);
            accountValidator.validateDate(dayString, monthString, yearString);

            List<String> errors = accountValidator.getErrors();
            final String message;
            if(errors.isEmpty()) {
                Date date = createDate(Integer.parseInt(dayString), Integer.parseInt(monthString),
                        Integer.parseInt(yearString));
                Account account = new AccountBuilder()
                        .setCardNumber(cardNumber)
                        .setClientId(Long.parseLong(idString))
                        .setType(selectedType)
                        .setAmountOfMoney(Long.parseLong(moneyString))
                        .setDateOfCreation(date)
                        .build();
                if(accountService.save(account)) {
                    message = "Account added successfully";
                } else {
                    message = UNEXPECTED_ERROR;
                }
            }
            else {
                message = accountValidator.getFormattedErrors();
            }
            JOptionPane.showMessageDialog(view.getContentPane(), message);
        }
    }

    private class TransferButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String from = view.getFrom();
            String to = view.getTo();
            String sum = view.getSum();

            transferValidator.validate(from, to, sum);
            List<String> errors = transferValidator.getErrors();
            final String message;

            if(errors.isEmpty()) {
                Long fromLong = transferValidator.getFromLong();
                Long toLong = transferValidator.getToLong();
                Long sumLong = transferValidator.getSumLong();
                if(accountService.transferBetweenAccounts(fromLong, toLong, sumLong)) {
                    message = "Successful transfer";
                }
                else {
                    message = UNEXPECTED_ERROR;
                }
            } else {
                message = transferValidator.getFormattedErrors();
            }
            JOptionPane.showMessageDialog(view.getContentPane(), message);
        }
    }

    private void deleteAccount(Long id) {
        final String message;
        if(accountService.remove(id)) {
            message = "Account deleted";
        } else {
            message = UNEXPECTED_ERROR;
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class DeleteAccountButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getAccountId();

            accountValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            UserController.this::deleteAccount,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), accountValidator.getFormattedErrors())
                    );
        }
    }

    private void processBill(Long id) {
        String costString = view.getBillCost();
        String message;
        try {
            Long cost = Long.parseLong(costString);
            if(accountService.processBill(id, cost)) {
                message = "Bill processed";
            } else {
                message = UNEXPECTED_ERROR;
            }
        } catch(NumberFormatException e) {
            message = "Bill cost must be a number";
        }
        JOptionPane.showMessageDialog(view.getContentPane(), message);
    }

    private class ProcessBillButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idString = view.getAccountId();
            accountValidator.validateIdToUpdate(idString)
                    .ifPresentOrElse(
                            UserController.this::processBill,
                            () -> JOptionPane.showMessageDialog(view.getContentPane(), accountValidator.getFormattedErrors())
                    );
        }
    }

    private class UserLogoutButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.changeToLoginPanel();
        }
    }

}
