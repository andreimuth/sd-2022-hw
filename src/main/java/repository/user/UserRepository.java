package repository.user;

import controller.response.Response;
import model.Role;
import model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean save(User user);

    void removeAll();

    Response<Boolean> existsByUsername(String email);

    List<User> findByRole(Role role);

    Optional<User> findById(Long id);

    Optional<User> findByIdAndRole(Long id, Role role);

    boolean update(Long id, String column, String newValue);

    boolean delete(Long id);

}
