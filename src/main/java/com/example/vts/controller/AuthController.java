package com.example.vts.controller;

import com.example.vts.entity.User;
import com.example.vts.jwt.JwtTokenProvider;
import com.example.vts.request.AuthRequest;
import com.example.vts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    private ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            User user = userService.getUser(email);
            if (user == null) {
                throw new UsernameNotFoundException("Username not found");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(email);
        final String token = new JwtTokenProvider().generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}