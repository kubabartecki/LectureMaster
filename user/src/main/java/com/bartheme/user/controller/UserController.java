package com.bartheme.user.controller;

import com.bartheme.user.model.ApplicationUserDto;
import com.bartheme.user.model.User;
import com.bartheme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        User userByUsername = userService.getUserByUsername(username);
        return ResponseEntity.ok(userByUsername);
    }

    @GetMapping("get")
    public Optional<ApplicationUserDto> getApplicationUserDtoByUsername(@RequestParam String username) {
        Optional<ApplicationUserDto> userByUsername =
                Optional.ofNullable(userService.getApplicationUserDtoByUsername(username));
        return userByUsername;
    }

    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                           @RequestBody Map<String, String> body) {
        User user = userService.updateUser(id, body);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("remove/{id}")
    public ResponseEntity<Boolean> removeUser(@PathVariable Integer id) {
        Boolean isRemoved = userService.deleteUser(id);
        return ResponseEntity.ok(isRemoved);
    }
}
