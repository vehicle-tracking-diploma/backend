package com.example.vts.controller;

import com.example.vts.entity.Car;
import com.example.vts.entity.User;
import com.example.vts.request.AssignCarRequest;
import com.example.vts.service.CarService;
import com.example.vts.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class UserCarController {
    UserService userService;
    CarService carService;


    @PostMapping("/assign/car")
    private ResponseEntity<?> assign_vehicle_to_user(@RequestBody AssignCarRequest assignRequest) {
        User user = userService.getUser(assignRequest.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User not found");
        }
        List<String> govIds = assignRequest.getGovIds();
        List<Car> cars;
        if (user.getCars() == null) {
            cars = new ArrayList<>();
        } else {
            cars = user.getCars();
        }
        for (String govId : govIds) {
            Car car = carService.getCarByGovID(govId);
            if (cars.contains(car)) continue;
            cars.add(car);
        }
        user.setCars(cars);
        userService.saveCars(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/user/cars")
    public ResponseEntity<?> getUserCars(@RequestParam String email) {
        User user = userService.getUser(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User not found");
        }
        List<Car> cars = user.getCars();
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }
}
