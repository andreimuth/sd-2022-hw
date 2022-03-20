package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
    }

    @BeforeEach
    public void cleanUp() {
        accountRepository.removeAll();
        clientRepository.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, accountRepository.findAll().size());
    }

    @Test
    public void findAllWhenDbNotEmpty() {
        Client client = new ClientBuilder()
                .setPersonalNumericalCode("123")
                .setName("test")
                .setAddress("home")
                .build();
        clientRepository.save(client);
        Account account = new AccountBuilder()
                .setCardNumber("123")
                .setClientId(client.getId())
                .setType("visa")
                .setAmountOfMoney(100L)
                .setDateOfCreation(new Date())
                .build();
        accountRepository.save(account);
        assertEquals(1, accountRepository.findAll().size());
    }

    @Test
    public void save() {
        Client client = new ClientBuilder()
                .setPersonalNumericalCode("123")
                .setName("test")
                .setAddress("home")
                .build();
        clientRepository.save(client);
        Account account = new AccountBuilder()
                .setCardNumber("123")
                .setClientId(client.getId())
                .setType("visa")
                .setAmountOfMoney(100L)
                .setDateOfCreation(new Date())
                .build();
        assertTrue(accountRepository.save(account));
    }

}
