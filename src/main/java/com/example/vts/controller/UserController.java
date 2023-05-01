package com.example.vts.controller;

import com.example.vts.entity.Role;
import com.example.vts.entity.User;
import com.example.vts.repository.RoleRepository;
import com.example.vts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @PostMapping("/user")
    private ResponseEntity<?> save(@RequestBody User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        List<Role> usrRoles = new ArrayList<>();
        Role role = roleRepository.findRoleByName("ROLE_USER");
        usrRoles.add(role);
        newUser.setRoles(usrRoles);
        if (userService.saveUser(newUser) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this login exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered " + userService.saveUser(user));
    }
    @GetMapping("/user")
    private ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in");
        }
        String email = authentication.getName();
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(email));
    }
    @GetMapping("/users")
    private ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}
