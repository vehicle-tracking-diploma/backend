package com.example.vts.controller;

import com.example.vts.entity.Role;
import com.example.vts.entity.User;
import com.example.vts.repository.RoleRepository;
import com.example.vts.request.AssignRoleRequest;
import com.example.vts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserRoleController {
    UserService userService;
    RoleRepository roleRepository;

    @PostMapping("/assign/role")
    private ResponseEntity<?> assign_role_to_user(@RequestBody AssignRoleRequest assignRoleRequest) {
        User user = userService.getUser(assignRoleRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User not found");
        }
        List<String> roles = assignRoleRequest.getRoles();
        List<Role> user_roles = user.getRoles();
        for (String role : roles) {
            Role cur_role = roleRepository.findRoleByName(role);
            if (user_roles.contains(cur_role)) continue;
            user_roles.add(cur_role);
        }
        user.setRoles(user_roles);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
