package com.example.vts.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignRoleRequest {
    private String email;
    private List<String> roles;
}
