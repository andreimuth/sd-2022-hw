import controller.AdminController;
import controller.LoginController;
import controller.UserController;
import database.DBConnectionFactory;
import database.JDBConnectionWrapper;
import model.User;
import model.validator.AccountValidator;
import model.validator.ClientValidator;
import model.validator.TransferValidator;
import model.validator.UserValidator;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.event.EventRepository;
import repository.event.EventRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.client.ClientService;
import service.client.ClientServiceImpl;
import service.event.EventService;
import service.event.EventServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import session.Session;
import view.View;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main {

    public static void main(String[] args) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);

        UserRepository userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        ClientRepository clientRepository = new ClientRepositoryMySQL(connection);
        AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
        EventRepository eventRepository = new EventRepositoryMySQL(connection);

        Session session = new Session();

        AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository, session);
        ClientService clientService = new ClientServiceImpl(clientRepository, session, eventRepository);
        AccountService accountService = new AccountServiceImpl(accountRepository, session, eventRepository);
        EventService eventService = new EventServiceImpl(eventRepository);

        UserValidator userValidator = new UserValidator(userRepository);
        ClientValidator clientValidator = new ClientValidator(clientRepository);
        AccountValidator accountValidator = new AccountValidator(accountRepository, clientRepository);
        TransferValidator transferValidator = new TransferValidator(accountRepository);

        View view = new View();

        LoginController loginController = new LoginController(view, authenticationService, userValidator);
        UserController userController = new UserController(view, clientService, accountService, clientValidator, accountValidator, transferValidator);
        AdminController adminController = new AdminController(view, authenticationService, userValidator, eventService);
    }

}
