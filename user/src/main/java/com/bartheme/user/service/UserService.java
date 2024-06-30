package com.bartheme.user.service;

import com.bartheme.user.model.ApplicationUserDto;
import com.bartheme.user.model.User;
import com.bartheme.user.model.UserDto;
import com.bartheme.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return null;
        }
        return userRepository.save(user);
    }

    public User updateUser(Integer id, Map<String, String> body) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return null;
        }
        User user = optionalUser.get();
        if (body.containsKey("username")) {
            user.setUsername(body.get("username"));
        }
        if (body.containsKey("password")) {
            user.setPassword(body.get("password"));
        }
        if (body.containsKey("email")) {
            user.setEmail(body.get("email"));
        }
        if (body.containsKey("firstName")) {
            user.setFirstName(body.get("firstName"));
        }
        if (body.containsKey("lastName")) {
            user.setLastName(body.get("lastName"));
        }
        if (body.containsKey("role")) {
            user.setRole(body.get("role"));
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public ApplicationUserDto getApplicationUserDtoByUsername(
            String username) {
        User user = getUserByUsername(username);
        if (user == null) {
            return null;
        }
        ApplicationUserDto authenticationRequest = new ApplicationUserDto();
        authenticationRequest.setUsername(user.getUsername());
        authenticationRequest.setPassword(user.getPassword());
        authenticationRequest.setRole(user.getRole());
        return authenticationRequest;
    }

    public UserDto getUserDtoById(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        return getUserDtoFromUserOptional(userOptional);
    }

    public UserDto getUserDtoByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return getUserDtoFromUserOptional(userOptional);
    }

    public User createStudent(String username, String password, String email, String firstName, String lastName) {
        User student = new User();
        student.setUsername(username);
        student.setPassword(password);
        student.setEmail(email);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setRole("STUDENT");
        return addUser(student);
    }

    private UserDto getUserDtoFromUserOptional(Optional<User> userOptional) {
        if (userOptional.isEmpty()) {
            return null;
        }
        User user = userOptional.get();
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

}
