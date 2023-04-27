package com.example.vts.service;

import com.example.vts.entity.Car;
import com.example.vts.entity.User;
import com.example.vts.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        boolean existsUser = userRepository.findByEmail(user.getEmail()).isPresent();
        if (existsUser) {
            return null;
        }
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUser(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<Car> getAllCars(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            User user = userRepository.findUserByEmail(email);
            return user.getCars();
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
