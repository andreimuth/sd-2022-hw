package model.validator;

import controller.response.Response;
import model.Account;
import model.Role;
import model.User;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static database.Constants.Roles.EMPLOYEE;

public class UserValidator {
    private static final String EMAIL_VALIDATION_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final int MIN_PASSWORD_LENGTH = 8;

    private final List<String> errors;
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
        errors = new ArrayList<>();
    }

    public void validate(String email, String password) {
        errors.clear();
        validateEmailUniqueness(email);
        validateEmail(email);
        validatePasswordLength(password);
        validatePasswordSpecial(password);
        validatePasswordDigit(password);
    }

    public void validateUpdate(String email, String password) {
        errors.clear();
        if(email.isBlank() && password.isBlank()) {
            errors.add("Update at least one field");
        } else {
            if(!email.isBlank()) {
                validateEmailUniqueness(email);
                validateEmail(email);
            }
            if(!password.isBlank()) {
                validatePasswordLength(password);
                validatePasswordSpecial(password);
                validatePasswordDigit(password);
            }
        }
    }


    public Optional<Long> validateIdToUpdate(String idString) {
        errors.clear();
        if(!idString.matches("[0-9]+")) {
            errors.add("Id must be an integer");
            return Optional.empty();
        }
        Long id = Long.parseLong(idString);
        Optional<User> optionalAccount = userRepository.findByIdAndRole(id, new Role(EMPLOYEE));
        if(optionalAccount.isEmpty()) {
            errors.add("Employee with id = " + id + " does not exist");
            return Optional.empty();
        }
        return Optional.of(id);
    }

    private void validateEmailUniqueness(String email) {
        final Response<Boolean> response = userRepository.existsByUsername(email);
        if (response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if (data) {
                errors.add("Email is already taken");
            }
        }
    }

    private void validateEmail(String email) {
        if (!email.matches(EMAIL_VALIDATION_REGEX)) {
            errors.add("Email is not valid");
        }
    }

    private void validatePasswordLength(String password) {
        if (!(password.length() >= MIN_PASSWORD_LENGTH)) {
            errors.add("Password must be at least 8 characters long");
        }
    }

    private void validatePasswordSpecial(String password) {
        if (!password.matches(".*[!@#$%^&*()_+].*")) {
            errors.add("Password must contain at least one special character");
        }
    }

    private void validatePasswordDigit(String password) {
        if (!password.matches(".*[0-9].*")) {
            errors.add("Password must contain at least one digit");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }
}
