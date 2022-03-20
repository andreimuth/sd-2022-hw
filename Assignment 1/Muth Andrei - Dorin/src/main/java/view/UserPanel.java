package view;

import table.TableModel;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import static database.Constants.AccountTypes.TYPES;

public class UserPanel extends JPanel {

    private JButton viewClientsButton;
    private JButton viewAccountsButton;
    private JButton addClientButton;
    private JButton updateButton;
    private JButton addAccountButton;
    private JButton updateAccountButton;
    private JButton transferButton;
    private JButton deleteAccountButton;
    private JButton processBillButon;
    private JButton logoutButton;
    private JTextField nameTextField;
    private JTextField cnTextField;
    private JTextField pncTextField;
    private JTextField addressTextField;
    private JComboBox<String> typeComboBox;
    private JTextField idTextField;
    private JTextField clientIdTextField;
    private JTextField moneyTextField;
    private JTextField dayTextField;
    private JTextField monthTextField;
    private JTextField yearTextField;
    private JTextField accountIdTextField;
    private JTextField fromTextField;
    private JTextField toTextField;
    private JTextField sumTextField;
    private JTextField billTextField;
    private JScrollPane clientsScrollPane = new JScrollPane();
    private JScrollPane accountsScrollPane = new JScrollPane();

    public UserPanel() {
        initializePanelAndButtons();
        addButtons();
        initializeClientFields();
        initializeAccountFields();
        initializeTransferFields();
        initializeBillField();
        addFieldsToPanel();
    }

    private void addButtons() {
        add(viewClientsButton);
        add(addClientButton);
        add(updateButton);
        add(viewAccountsButton);
        add(addAccountButton);
        add(updateAccountButton);
        add(transferButton);
        add(deleteAccountButton);
        add(processBillButon);
        add(logoutButton);
    }

    private void initializePanelAndButtons() {
        setLayout(null);
        viewClientsButton = new JButton("View clients");
        viewClientsButton.setBounds(10, 10, 120, 40);
        addClientButton = new JButton("Add client");
        addClientButton.setBounds(10, 130, 120, 40);
        updateButton = new JButton("Update client");
        updateButton.setBounds(10, 220, 120, 40);
        viewAccountsButton = new JButton("View accounts");
        viewAccountsButton.setBounds(10, 270, 120, 40);
        addAccountButton = new JButton("Add account");
        addAccountButton.setBounds(190, 300, 120, 40);
        updateAccountButton = new JButton("Update account");
        updateAccountButton.setBounds(315, 300, 140, 40);
        transferButton = new JButton("Transfer between accounts");
        transferButton.setBounds(460, 300, 200, 40);
        deleteAccountButton = new JButton("Delete account");
        deleteAccountButton.setBounds(665, 300, 120, 40);
        processBillButon = new JButton("Process bill");
        processBillButon.setBounds(665, 345, 120, 40);
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(665, 390, 120, 40);
    }

    private void addFieldsToPanel() {
        add(nameTextField);
        add(pncTextField);
        add(addressTextField);
        add(idTextField);
        add(cnTextField);
        add(clientIdTextField);
        add(typeComboBox);
        add(moneyTextField);
        add(dayTextField);
        add(monthTextField);
        add(yearTextField);
        add(accountIdTextField);
        add(fromTextField);
        add(toTextField);
        add(sumTextField);
        add(billTextField);
    }

    private void initializeClientFields() {
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(10, 55, 50, 20);
        nameTextField = new JTextField();
        nameTextField.setBounds(60, 55, 100, 20);
        JLabel pncLabel = new JLabel("PNC: ");
        pncLabel.setBounds(10, 80, 50, 20);
        pncTextField = new JTextField();
        pncTextField.setBounds(60, 80, 100, 20);
        JLabel addressLabel = new JLabel("Address: ");
        addressLabel.setBounds(10, 105, 55, 20);
        addressTextField = new JTextField();
        addressTextField.setBounds(60, 105, 100, 20);
        JLabel idLabel = new JLabel("<html>Client id<br>to update: </html>");
        idLabel.setBounds(10, 175, 65, 40);
        idTextField = new JTextField();
        idTextField.setBounds(70, 185, 90, 20);
        add(nameLabel);
        add(pncLabel);
        add(addressLabel);
        add(idLabel);
    }

    private void initializeAccountFields() {
        JLabel cnLabel = new JLabel("Card number:");
        cnLabel.setBounds(10, 315, 85, 20);
        cnTextField = new JTextField();
        cnTextField.setBounds(90, 315, 100, 20);
        JLabel idLabel = new JLabel("Client id:");
        idLabel.setBounds(10, 340, 50, 20);
        clientIdTextField = new JTextField();
        clientIdTextField.setBounds(90, 340, 100, 20);
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(10, 365, 50, 20);
        typeComboBox = new JComboBox<>(TYPES);
        typeComboBox.setBounds(90, 365, 100, 20);
        JLabel moneyLabel = new JLabel("Money:");
        moneyLabel.setBounds(10, 390, 50, 20);
        moneyTextField = new JTextField();
        moneyTextField.setBounds(90, 390, 100, 20);
        JLabel dayLabel = new JLabel("Day:");
        dayLabel.setBounds(10, 415, 25, 20);
        dayTextField = new JTextField();
        dayTextField.setBounds(40, 415, 50, 20);
        JLabel monthLabel = new JLabel("Month:");
        monthLabel.setBounds(95, 415, 40, 20);
        monthTextField = new JTextField();
        monthTextField.setBounds(135, 415, 50, 20);
        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(190, 415, 40, 20);
        yearTextField = new JTextField();
        yearTextField.setBounds(230, 415, 50, 20);
        JLabel accountLabel = new JLabel("Account id to update/delete/process bill:");
        accountLabel.setBounds(195, 340, 250, 20);
        accountIdTextField = new JTextField();
        accountIdTextField.setBounds(430, 340, 50, 20);
        add(cnLabel);
        add(idLabel);
        add(typeLabel);
        add(moneyLabel);
        add(dayLabel);
        add(monthLabel);
        add(yearLabel);
        add(accountLabel);
    }

    private void initializeTransferFields() {
        JLabel fromLabel = new JLabel("Transfer from account with id ");
        fromLabel.setBounds(250, 365, 200, 20);
        fromTextField = new JTextField();
        fromTextField.setBounds(420, 365, 40, 20);
        JLabel toLabel = new JLabel("to account with id ");
        toLabel.setBounds(465, 365, 130, 20);
        toTextField = new JTextField();
        toTextField.setBounds(570, 365, 40, 20);
        JLabel sumLabel = new JLabel("the sum:");
        sumLabel.setBounds(250, 390, 50, 20);
        sumTextField = new JTextField();
        sumTextField.setBounds(300, 390, 40, 20);
        add(fromLabel);
        add(toLabel);
        add(sumLabel);
    }

    private void initializeBillField() {
        JLabel billLabel = new JLabel("Enter bill cost:");
        billLabel.setBounds(490, 340, 100, 20);
        billTextField = new JTextField();
        billTextField.setBounds(580, 340, 40, 20);
        add(billLabel);
    }

    public void addViewClientsButtonListener(ActionListener viewClientsButtonListener) {
        viewClientsButton.addActionListener(viewClientsButtonListener);
    }

    public void addAddButtonListener (ActionListener addButtonListener) {
        addClientButton.addActionListener(addButtonListener);
    }

    public void addUpdateClientsButtonListener(ActionListener updateClientsButtonListener) {
        updateButton.addActionListener(updateClientsButtonListener);
    }

    public void addViewAccountsButtonListener(ActionListener viewAccountsButtonListener) {
        viewAccountsButton.addActionListener(viewAccountsButtonListener);
    }

    public void addAddAccountButtonListener(ActionListener addAccountButtonListener) {
        addAccountButton.addActionListener(addAccountButtonListener);
    }

    public void addUpdateAccountButtonListener(ActionListener updateAccountButtonListener) {
        updateAccountButton.addActionListener(updateAccountButtonListener);
    }

    public void addTransferButtonListener(ActionListener transferButtonActionListener) {
        transferButton.addActionListener(transferButtonActionListener);
    }

    public void addDeleteAccountButtonListener(ActionListener deleteAccountButtonListener) {
        deleteAccountButton.addActionListener(deleteAccountButtonListener);
    }

    public void addProcessBillButtonListener(ActionListener processBillButtonListener) {
        processBillButon.addActionListener(processBillButtonListener);
    }

    public void addLogoutButtonListener(ActionListener logoutButtonListener) {
        logoutButton.addActionListener(logoutButtonListener);
    }

    public void addClientsTable(TableModel tableModel) {
        clientsScrollPane.removeAll();
        remove(clientsScrollPane);
        JTable clientsTable = new JTable(tableModel);
        clientsScrollPane = new JScrollPane(clientsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        clientsScrollPane.setBounds(165, 10, 600, 100);
        add(clientsScrollPane);
        repaint();
        revalidate();
    }

    public void addAccountsTable(TableModel tableModel) {
        accountsScrollPane.removeAll();
        remove(accountsScrollPane);
        JTable accountsTable = new JTable(tableModel);
        accountsScrollPane = new JScrollPane(accountsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        accountsScrollPane.setBounds(170, 150, 600, 100);
        add(accountsScrollPane);
        repaint();
        revalidate();
    }

    public void addTypeListener(ItemListener typeListener) {
        typeComboBox.addItemListener(typeListener);
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getId() {
        return idTextField.getText();
    }

    public String getPersonalNumericalCode() {
        return pncTextField.getText();
    }

    public String getAddress() {
        return addressTextField.getText();
    }

    public String getCardNumber() {
        return cnTextField.getText();
    }

    public String getClientId() {
        return clientIdTextField.getText();
    }

    public String getDay() {
        return dayTextField.getText();
    }

    public String getMonth() {
        return monthTextField.getText();
    }

    public String getYear() {
        return yearTextField.getText();
    }

    public String getMoney() {
        return moneyTextField.getText();
    }

    public String getAccountId() {
        return accountIdTextField.getText();
    }

    public String getFrom() {
        return fromTextField.getText();
    }

    public String getTo() {
        return toTextField.getText();
    }

    public String getSum() {
        return sumTextField.getText();
    }

    public String getBillCost() {
        return billTextField.getText();
    }

}
