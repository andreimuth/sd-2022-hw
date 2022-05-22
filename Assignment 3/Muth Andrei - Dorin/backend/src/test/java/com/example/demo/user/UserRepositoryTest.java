package com.example.demo.user;

import com.example.demo.TestCreationFactory;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.Set;

import static com.example.demo.TestCreationFactory.randomEmail;
import static com.example.demo.TestCreationFactory.randomString;
import static com.example.demo.user.model.ERole.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void existsByEmail() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();

        userRepository.save(user);

        assertTrue(userRepository.existsByEmail(user.getEmail()));
    }

    @Test
    void findByUsername() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();
        userRepository.save(user);
        Optional<User> optional = userRepository.findByUsername(user.getUsername());
        assertTrue(optional.isPresent());
    }

    @Test
    void existsByUsername() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();

        userRepository.save(user);

        assertTrue(userRepository.existsByUsername(user.getUsername()));
    }

    @Test
    void findByEmail() {
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();
        userRepository.save(user);
        Optional<User> optional = userRepository.findByEmail(user.getEmail());
        assertTrue(optional.isPresent());
    }

    @Test
    void findAllByRolesContaining() {
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
        User user = User.builder().username(randomString()).password(randomString()).email(randomEmail()).build();
        Optional<Role> role = roleRepository.findByName(USER);
        assertTrue(role.isPresent());
        user.setRoles(Set.of(role.get()));
        userRepository.save(user);
        Page<User> users = userRepository.findAllByRolesContaining(role.get(), PageRequest.of(0, 10));
        assertEquals(1, users.getContent().size());
    }

}
