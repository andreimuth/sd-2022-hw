package com.example.demo.user;

import com.example.demo.security.dto.MessageResponse;
import com.example.demo.user.dto.UserListDto;
import com.example.demo.user.dto.UserUpdateDto;
import com.example.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.example.demo.UrlMapping.*;

@CrossOrigin
@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDto> allEmployees() {
        return userService.allUsersForList();
    }

    @PutMapping(UPDATE_USER)
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        Optional<User> emailUser = userService.findByEmail(userUpdateDto.getEmail());

        if(emailUser.isPresent() && !Objects.equals(emailUser.get().getId(), id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email already exists"));
        }

        Optional<User> usernameUser = userService.findByUsername(userUpdateDto.getUsername());

        if(usernameUser.isPresent() && !Objects.equals(usernameUser.get().getId(), id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username already exists"));
        }

        return ResponseEntity.ok(userService.updateUser(id, userUpdateDto));
    }

    @DeleteMapping(DELETE_USER)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}