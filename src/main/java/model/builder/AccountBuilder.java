package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {

    private final Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setCardNumber(String cardNumber) {
        account.setCardNumber(cardNumber);
        return this;
    }

    public AccountBuilder setClientId(Long clientId) {
        account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setAmountOfMoney(Long amountOfMoney) {
        account.setAmountOfMoney(amountOfMoney);
        return this;
    }

    public AccountBuilder setDateOfCreation(Date dateOfCreation) {
        account.setDateOfCreation(dateOfCreation);
        return this;
    }

    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }

    public AccountBuilder setId(Long id) {
        account.setId(id);
        return this;
    }

    public Account build() {
        return account;
    }

}
