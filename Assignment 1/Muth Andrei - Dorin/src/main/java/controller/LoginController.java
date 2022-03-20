package controller;

import model.User;
import model.validator.UserValidator;
import service.user.AuthenticationService;
import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

import static database.Constants.Roles.*;

public class LoginController {

    private final View view;
    private final AuthenticationService authenticationService;
    private final UserValidator userValidator;
    public LoginController(View view, AuthenticationService authenticationService, UserValidator userValidator) {
        this.view = view;
        this.authenticationService = authenticationService;
        this.userValidator = userValidator;

        this.view.addLoginButtonListener(new LoginButtonListener());
        this.view.addRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail();
            String password = view.getPassword();

            Optional<User> optionalUser = authenticationService.login(email, password);
            if(optionalUser.isPresent()) {
                if(optionalUser.get().getRoles().get(0).getRole().equals(EMPLOYEE)) {
                    view.changeToUserPanel();
                } else {
                    view.changeToAdminPanel();
                }
            }
            else {
                JOptionPane.showMessageDialog(view.getContentPane(), "Invalid combination of email " +
                        "and password");
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmail();
            String password = view.getPassword();

            userValidator.validate(email, password);
            List<String> errors = userValidator.getErrors();
            final String message;
            if(errors.isEmpty()) {
                if(authenticationService.register(email, password)) {
                    message = "Registration successful";
                }
                else {
                    message = "Something went wrong";
                }
            }
            else {
                message = userValidator.getFormattedErrors();
            }
            JOptionPane.showMessageDialog(view.getContentPane(), message);
        }
    }

}
