package model.validator;

import model.Account;
import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TransferValidator {

    private List<String> errors;
    private Long fromLong, toLong, sumLong;
    private final AccountRepository accountRepository;

    public TransferValidator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        errors = new ArrayList<>();
    }

    public void validate(String from, String to, String sum) {
        errors.clear();
        validateFormat(from, to, sum);
        validateExistence();
        if(errors.isEmpty()) {
            validateEnoughMoney();
        }
    }

    private void validateFormat(String from, String to, String sum) {
        try {
            fromLong = Long.parseLong(from);
            toLong = Long.parseLong(to);
            sumLong = Long.parseLong(sum);
        } catch (NumberFormatException e) {
            errors.add("\'From\', \'to\' and \'sum\' fields must be numbers");
        }
    }

    private void validateExistence() {
        if(accountRepository.findById(fromLong).isEmpty()) {
            errors.add("Account with id = " + fromLong + " does not exist");
        }
        if(accountRepository.findById(toLong).isEmpty()) {
            errors.add("Account with id = " + toLong + " does not exist");
        }
    }

    private void validateEnoughMoney() {
        if(accountRepository.findById(fromLong).get().getAmountOfMoney() < sumLong) {
            errors.add("Not enough money in the account with id = " + fromLong);
        }
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getFormattedErrors() {
        return String.join("\n", errors);
    }

    public Long getFromLong() {
        return fromLong;
    }

    public void setFromLong(Long fromLong) {
        this.fromLong = fromLong;
    }

    public Long getToLong() {
        return toLong;
    }

    public void setToLong(Long toLong) {
        this.toLong = toLong;
    }

    public Long getSumLong() {
        return sumLong;
    }

    public void setSumLong(Long sumLong) {
        this.sumLong = sumLong;
    }
}
