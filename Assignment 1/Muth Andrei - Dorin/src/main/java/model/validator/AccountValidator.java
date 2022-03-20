package model.validator;

import controller.response.Response;
import model.Account;
import model.Client;
import repository.account.AccountRepository;
import repository.client.ClientRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountValidator {

    private final List<String> errors;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;
    private final int CARD_NUMBER_LENGTH = 16;
    private static final String NUMBER_REGEX = "[0-9]+";

    public AccountValidator(AccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        errors = new ArrayList<>();
    }

    public Optional<Long> validateIdToUpdate(String idString) {
        errors.clear();
        if(!idString.matches("[0-9]+")) {
            errors.add("Id must be an integer");
            return Optional.empty();
        }
        Long id = Long.parseLong(idString);
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isEmpty()) {
            errors.add("Account with id = " + id + " does not exist");
            return Optional.empty();
        }
        return Optional.of(id);
    }

    public void validateDateUpdateExistence(String dayString, String monthString, String yearString) {
        if(!dayString.isBlank() && !monthString.isBlank() && !yearString.isBlank()) {
            validateDate(dayString, monthString, yearString);
        }
    }

    public void validateNonDateFields(String cardNumber, String idString, String moneyString) {
        validateCardNumberLength(cardNumber);
        validateCardNumber(cardNumber);
        validateCardNumberUniqueness(cardNumber);
        validateClientId(idString);
        validateMoney(moneyString);
    }

    public void validateDate(String dayString, String monthString, String yearString) {
        if(!dayString.matches(NUMBER_REGEX) || !monthString.matches(NUMBER_REGEX) || !yearString.matches(NUMBER_REGEX)) {
            errors.add("Day, month and year must be integers");
            return;
        }
        int day = Integer.parseInt(dayString);
        int month = Integer.parseInt(monthString);
        int year = Integer.parseInt(yearString);
        try {
            LocalDate localDate = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            errors.add("Date is invalid");
        }
    }

    public void validateCardNumberLength(String cardNumber) {
        if(cardNumber.length() != CARD_NUMBER_LENGTH) {
            errors.add("Card number must consist of 16 digits");
        }
    }

    public void validateCardNumber(String cardNumber) {
        if(!cardNumber.matches(NUMBER_REGEX)) {
            errors.add("Card number must contain only digits");
        }
    }

    public void validateMoney(String moneyString) {
        try {
            Long money = Long.parseLong(moneyString);
        } catch (NumberFormatException e) {
            errors.add("Money field must be Long");
        }
    }

    public void validateCardNumberUniqueness(String cardNumber) {
        final Response<Boolean> response = accountRepository.existsByCardNumber(cardNumber);
        if(response.hasErrors()) {
            errors.add(response.getFormattedErrors());
        } else {
            final Boolean data = response.getData();
            if(data) {
                errors.add("Card numbers must be unique");
            }
        }
    }

    public void validateClientId(String idString) {
        if(!idString.matches(NUMBER_REGEX)) {
            errors.add("Client id must be an integer");
            return;
        }
        Long clientId = Long.parseLong(idString);
        if(clientRepository.findById(clientId).isEmpty()) {
            errors.add("Client with id = " + clientId + " does not exist");
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

}
