package com.example.demo.user;

import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.dto.UserMinimalDto;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.model.ERole;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.demo.user.model.ERole.EMPLOYEE;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    public List<UserMinimalDto> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDto> allUsersForList() {
        Role employeeRole = roleRepository.findByName(EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Employee role not found"));
        return userRepository.findAllByRolesContaining(employeeRole, PageRequest.of(0, 10))
                .stream().map(user -> {
                    UserListDto userListDto = userMapper.userListDtoFromUser(user);
                    userMapper.populateRoles(user, userListDto);
                    return userListDto;
                })
                .collect(toList());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserUpdateDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = findById(id);
        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setPassword(encoder.encode(userUpdateDto.getPassword()));

        return userMapper.userUpdateDtoFromUser(userRepository.save(user));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User with id = " + id + "not found"));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}