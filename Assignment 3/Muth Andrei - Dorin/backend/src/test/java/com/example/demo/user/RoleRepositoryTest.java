package com.example.demo.user;

import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.demo.user.model.ERole.ADMIN;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
        for (ERole value : ERole.values()) {
            roleRepository.save(
                    Role.builder()
                            .name(value)
                            .build()
            );
        }
    }

    @Test
    void findByName() {
        Optional<Role> optional = roleRepository.findByName(ADMIN);
        assertTrue(optional.isPresent());
    }

}
