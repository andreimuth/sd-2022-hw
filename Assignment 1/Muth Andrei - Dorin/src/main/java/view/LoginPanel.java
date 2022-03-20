package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginPanel() {
        initializePanelAndFields();
        initializeButtons();
    }

    private void initializePanelAndFields() {
        setLayout(null);
        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setBounds(45, 80, 40, 20);
        emailTextField = new JTextField();
        emailTextField.setBounds(135, 80, 100, 20);
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(45, 105, 80, 20);
        passwordField = new JPasswordField();
        passwordField.setBounds(135, 105, 100, 20);
        add(emailLabel);
        add(emailTextField);
        add(passwordLabel);
        add(passwordField);
    }

    private void initializeButtons() {
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 200, 120, 40);
        registerButton = new JButton("Register");
        registerButton.setBounds(155, 200, 120, 40);
        add(loginButton);
        add(registerButton);
    }

    public void addLoginButtonListener(ActionListener loginButtonListener) {
        loginButton.addActionListener(loginButtonListener);
    }

    public void addRegisterButtonListener(ActionListener registerButtonListener) {
        registerButton.addActionListener(registerButtonListener);
    }

    public String getEmail() {
        return emailTextField.getText();
    }

    public String getPassword() {
        return String.valueOf(passwordField.getPassword());
    }

}
