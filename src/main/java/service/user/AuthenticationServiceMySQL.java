package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import session.Session;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static database.Constants.Roles.EMPLOYEE;

public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final Session session;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository, Session session) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.session = session;
    }

    @Override
    public boolean register(String email, String password) {
        String encodedPassword = encodePassword(password);

        Role customerRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);

        User user = new UserBuilder()
                .setEmail(email)
                .setPassword(encodedPassword)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, encodePassword(password));
        optionalUser.ifPresent(session::setLoggedUser);
        return optionalUser;
    }

    @Override
    public void logout() {
        session.logout();
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<User> findByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public boolean update(Long id, String email, String password) {
        if(!email.isBlank()) {
            if(!userRepository.update(id, "email", email)) {
                return false;
            }
        }
        if(!password.isBlank()) {
            return userRepository.update(id, "password", encodePassword(password));
        }
        return true;
    }

    @Override
    public boolean remove(Long id) {
        return userRepository.delete(id);
    }

}
