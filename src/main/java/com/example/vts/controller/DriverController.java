package com.example.vts.controller;

import com.example.vts.service.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class DriverController {
    DriverService driverService;

    public ResponseEntity<?> getAllDrivers() {
        return ResponseEntity.status(HttpStatus.OK).body(driverService.getAllDrivers());
    }
    
}
