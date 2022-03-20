package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;

    @BeforeAll
    public static void setUp() {
        clientRepository = new ClientRepositoryMySQL(new DBConnectionFactory().getConnectionWrapper(true).getConnection());
    }

    @BeforeEach
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void findAll() {
        assertEquals(0, clientRepository.findAll().size());
    }

    @Test
    public void findAllWhenDbNotEmpty() {
        Client client = new ClientBuilder()
                .setPersonalNumericalCode("123")
                .setName("test")
                .setAddress("home")
                .build();
        clientRepository.save(client);
        assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    public void save() {
        Client client = new ClientBuilder()
                .setPersonalNumericalCode("123")
                .setName("test")
                .setAddress("home")
                .build();
        assertTrue(clientRepository.save(client));
    }

}
