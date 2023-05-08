package com.example.vts.service;

import com.example.vts.entity.Car;
import com.example.vts.entity.Driver;
import com.example.vts.repository.DriverRepository;
import com.example.vts.request.DriverRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DriverService {
    DriverRepository driverRepository;
    CarService carService;

    public Driver addDriver(DriverRequest driverRequest) {
        Driver driver1 = new Driver();
        if (driverRepository.existsByPhone(driverRequest.getPhone())) {
            return null;
        }
        driver1.setName(driverRequest.getName());
        driver1.setLastname(driverRequest.getLastname());
        driver1.setPhone(driverRequest.getPhone());
        Car car = carService.getCarByGovID(driverRequest.getGovId());
        driverRepository.save(driver1);
        carService.addDriver(car,driver1);

        return driver1;
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
}
