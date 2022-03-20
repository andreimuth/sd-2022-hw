package service.user;

import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

public interface AuthenticationService {

    boolean register(String email, String password);

    Optional<User> login(String email, String password);

    void logout();

    List<User> findByRole(Role role);

    boolean update(Long id, String email, String password);

    boolean remove(Long id);

}
