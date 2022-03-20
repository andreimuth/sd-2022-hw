package service.client;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

import static database.Constants.Roles.EMPLOYEE;
import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceImplTest {

    private static ClientService clientService;

    @BeforeAll
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        EventRepository eventRepository = new EventRepositoryMySQL(connection);
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
        clientService = new ClientServiceImpl(clientRepository, session, eventRepository);
        clientRepository.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, clientService.findAll().size());
    }

    @Test
    public void findById() {
        Long id = 1L;
        assertTrue(clientService.findById(id).isEmpty());
    }

    @Test
    public void save() {
        String name = "Name";
        String personalNumericalCode = "1234567890123";
        String address = "Cluj-Napoca";
        assertTrue(clientService.save(name, personalNumericalCode, address));
    }

}
