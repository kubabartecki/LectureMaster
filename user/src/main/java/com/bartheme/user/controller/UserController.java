package com.bartheme.user.controller;

import com.bartheme.user.model.ApplicationUserDto;
import com.bartheme.user.model.User;
import com.bartheme.user.model.UserDto;
import com.bartheme.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping("all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping
    public ResponseEntity<User> getUserByUsername(@RequestParam String username) {
        User userByUsername = userService.getUserByUsername(username);
        return ResponseEntity.ok(userByUsername);
    }

    @PreAuthorize("hasAuthority('admin:write')")
    @PostMapping("add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User addedUser = userService.addUser(user);
        return ResponseEntity.ok(addedUser);
    }

    @PreAuthorize("hasAuthority('admin:write')")
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Integer id,
                                           @RequestBody Map<String, String> body) {
        User user = userService.updateUser(id, body);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('admin:write')")
    @DeleteMapping("remove/{id}")
    public ResponseEntity<Boolean> removeUser(@PathVariable Integer id) {
        Boolean isRemoved = userService.deleteUser(id);
        return ResponseEntity.ok(isRemoved);
    }

    // for other microservice
    // todo ban access in api gateway
    @GetMapping("get")
    public Optional<ApplicationUserDto> getApplicationUserDtoByUsername(@RequestParam String username) {
        Optional<ApplicationUserDto> userByUsername =
                Optional.ofNullable(userService.getApplicationUserDtoByUsername(username));
        return userByUsername;
    }

    @GetMapping("get/details")
    public Optional<UserDto> getUserDtoByIdOrUsername(@RequestParam(required = false) Integer id,
                                            @RequestParam(required = false) String username) {
        if (id == null && username == null) {
            return Optional.empty();
        }
        if (id != null) {
            System.out.println("id" + id);
            return Optional.ofNullable(userService.getUserDtoById(id));
        }
        if (username.isEmpty()) {
            throw new IllegalArgumentException("username is empty");
        }
        return Optional.ofNullable(userService.getUserDtoByUsername(username));
    }
}
