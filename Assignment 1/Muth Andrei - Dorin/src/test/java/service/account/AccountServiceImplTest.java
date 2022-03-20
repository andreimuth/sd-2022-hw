package service.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.Role;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.client.ClientRepositoryMySQLTest;
import repository.event.EventRepository;
import repository.event.EventRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import session.Session;

import java.sql.Connection;
import java.util.Collections;
import java.util.Date;

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceImplTest {

    private static AccountService accountService;
    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        EventRepository eventRepository = new EventRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        Session session = new Session();
        Role role = new Role(EMPLOYEE);
        role.setId(1L);
        role.setRights(rightsRolesRepository.findRightsByRole(1L));
        User user = new UserBuilder()
                .setEmail("a@a.com").setPassword("bla").setRoles(Collections.singletonList(role)).build();
        userRepository.removeAll();
        userRepository.save(user);
        session.setLoggedUser(user);
        accountService = new AccountServiceImpl(accountRepository, session, eventRepository);
        accountRepository.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, accountService.findAll().size());
    }

    @Test
    public void findById() {
        Long id = 1L;
        assertTrue(accountService.findById(id).isEmpty());
    }

    @Test
    public void save() {
        clientRepository.removeAll();
        Client client = new ClientBuilder().setName("client").setAddress("Cluj").setPersonalNumericalCode("123").build();
        clientRepository.save(client);
        Account account = new AccountBuilder()
                .setCardNumber("123")
                .setClientId(client.getId())
                .setType("visa")
                .setDateOfCreation(new Date())
                .setAmountOfMoney(100L)
                .build();
        assertTrue(accountService.save(account));
    }

}
