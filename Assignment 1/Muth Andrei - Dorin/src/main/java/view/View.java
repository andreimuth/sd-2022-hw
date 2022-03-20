package view;

import table.TableModel;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Date;

public class View extends JFrame {

    private final LoginPanel loginPanel;
    private final UserPanel userPanel;
    private final AdminPanel adminPanel;

    public View() {
        loginPanel = new LoginPanel();
        userPanel = new UserPanel();
        adminPanel = new AdminPanel();
        setSize(300, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(loginPanel);
        setVisible(true);
    }

    public void changeToUserPanel() {
        setSize(900, 500);
        getContentPane().removeAll();
        add(userPanel);
        repaint();
    }

    public void changeToLoginPanel() {
        setSize(300, 300);
        getContentPane().removeAll();
        add(loginPanel);
        repaint();
    }

    public void changeToAdminPanel() {
        setSize(900, 500);
        getContentPane().removeAll();
        add(adminPanel);
        repaint();
    }

    public void addLoginButtonListener(ActionListener loginButtonListener) {
        loginPanel.addLoginButtonListener(loginButtonListener);
    }

    public void addRegisterButtonListener(ActionListener registerButtonListener) {
        loginPanel.addRegisterButtonListener(registerButtonListener);
    }

    public void addViewClientsButtonListener(ActionListener viewClientsButtonListener) {
        userPanel.addViewClientsButtonListener(viewClientsButtonListener);
    }

    public void addAddButtonListener (ActionListener addButtonListener) {
        userPanel.addAddButtonListener(addButtonListener);
    }

    public void addUpdateClientsButtonListener(ActionListener updateClientsButtonListener) {
        userPanel.addUpdateClientsButtonListener(updateClientsButtonListener);
    }

    public void addViewAccountsButtonListener(ActionListener viewAccountsButtonListener) {
        userPanel.addViewAccountsButtonListener(viewAccountsButtonListener);
    }

    public void addAddAccountButtonListener(ActionListener addAccountButtonListener) {
        userPanel.addAddAccountButtonListener(addAccountButtonListener);
    }

    public void addUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        userPanel.addUpdateAccountButtonListener(updateAccountButtonListener);
    }

    public void addTransferButtonListener(ActionListener transferButtonActionListener) {
        userPanel.addTransferButtonListener(transferButtonActionListener);
    }

    public void addDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        userPanel.addDeleteAccountButtonListener(deleteAccountButtonListener);
    }

    public void addProcessBillButtonListener(ActionListener processBillButtonListener) {
        userPanel.addProcessBillButtonListener(processBillButtonListener);
    }

    public void addUserLogoutButtonListener(ActionListener logoutButtonListener) {
        userPanel.addLogoutButtonListener(logoutButtonListener);
    }

    public void addAddEmployeeButtonListener(ActionListener addEmployeeButtonListener) {
        adminPanel.addAddEmployeeButtonListener(addEmployeeButtonListener);
    }

    public void addUpdateEmployeeButtonListener(ActionListener updateEmployeeButtonListener) {
        adminPanel.addUpdateEmployeeButtonListener(updateEmployeeButtonListener);
    }

    public void addFilterButtonListener(ActionListener filterButtonListener) {
        adminPanel.addFilterButtonListener(filterButtonListener);
    }

    public void addAdminLogoutButtonListener(ActionListener logoutButtonListener) {
        adminPanel.addLogoutButtonListener(logoutButtonListener);
    }

    public void addTypeListener(ItemListener typeListener) {
        userPanel.addTypeListener(typeListener);
    }

    public String getEmail() {
        return loginPanel.getEmail();
    }

    public String getPassword() {
        return loginPanel.getPassword();
    }

    public void addClientsTable(TableModel tableModel) {
        userPanel.addClientsTable(tableModel);
    }

    public void addAccountsTable(TableModel tableModel) {
        userPanel.addAccountsTable(tableModel);
    }

    public void addEmployeesTable(TableModel tableModel) {
        adminPanel.addEmployeesTable(tableModel);
    }

    public void addEventsTable(TableModel tableModel) {
        adminPanel.addEventsTable(tableModel);
    }

    public void addViewEmployeesButtonListener(ActionListener viewEmployeesButtonListener) {
        adminPanel.addViewEmployeesButtonListener(viewEmployeesButtonListener);
    }

    public void addDeleteEmployeeButtonListener(ActionListener deleteEmployeeButtonListener) {
        adminPanel.addDeleteEmployeeButtonListener(deleteEmployeeButtonListener);
    }

    public void addViewEventsButtonListener(ActionListener viewEventsButtonListener) {
        adminPanel.addViewEventsButtonListener(viewEventsButtonListener);
    }


    public String getName() {
        return userPanel.getName();
    }

    public String getPersonalNumericalCode() {
        return userPanel.getPersonalNumericalCode();
    }

    public String getAddress() {
        return userPanel.getAddress();
    }

    public String getId() {
        return userPanel.getId();
    }

    public String getCardNumber() {
        return userPanel.getCardNumber();
    }

    public String getClientId() {
        return userPanel.getClientId();
    }

    public String getDay() {
        return userPanel.getDay();
    }

    public String getMonth() {
        return userPanel.getMonth();
    }

    public String getYear() {
        return userPanel.getYear();
    }

    public String getMoney() {
        return userPanel.getMoney();
    }

    public String getAccountId() {
        return userPanel.getAccountId();
    }

    public String getFrom() {
        return userPanel.getFrom();
    }

    public String getTo() {
        return userPanel.getTo();
    }

    public String getSum() {
        return userPanel.getSum();
    }

    public String getBillCost() {
        return userPanel.getBillCost();
    }

    public String getEmailFromAdminPanel() {
        return adminPanel.getEmail();
    }

    public String getPasswordFromAdminPanel() {
        return adminPanel.getPassword();
    }

    public String getUserId() {
        return adminPanel.getUserId();
    }

    public Date getFromDate() {
        return adminPanel.getFromDate();
    }

    public Date getToDate() {
        return adminPanel.getToDate();
    }

}
