package com.example.vts.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignCarRequest {
    private String email;
    private List<String> govIds;
}
