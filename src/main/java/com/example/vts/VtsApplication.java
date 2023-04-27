package com.example.vts;

import com.example.vts.entity.Role;
import com.example.vts.entity.User;
import com.example.vts.repository.RoleRepository;
import com.example.vts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableWebMvc
public class VtsApplication implements CommandLineRunner {
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(VtsApplication.class, args);
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {

        Role usrRole = new Role();
        usrRole.setName("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.save(usrRole);
        roleRepository.save(adminRole);

        User admin = new User();
        admin.setFirstname("Amir");
        admin.setLastname("Ergaliev");
        List<Role> roles = new ArrayList<>();
        Role role = roleRepository.findRoleByName("ROLE_ADMIN");
        roles.add(role);
        admin.setRoles(roles);
        admin.setEmail("AmirKing@mail.ru");
        admin.setPassword(passwordEncoder.encode("1234"));
        userService.saveUser(admin);

    }
}
