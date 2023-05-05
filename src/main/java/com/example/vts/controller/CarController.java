package com.example.vts.controller;

import com.example.vts.entity.Car;
import com.example.vts.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/")
public class CarController {

    CarService carService;

    @GetMapping("/cars")
    public ResponseEntity<?> getAll() {
        List<Car> cars = carService.getAll();
        if (cars == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No cars");
        }
        return ResponseEntity.status(HttpStatus.OK).body(cars);
    }
    @PostMapping("/car")
    public ResponseEntity<?> addCar(@RequestBody Car car) {
        Car newCar = carService.addCar(car);
        if (newCar == null) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The car with this id already exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(newCar);
    }
}
