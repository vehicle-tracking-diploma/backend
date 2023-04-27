package com.example.vts.service;

import com.example.vts.entity.Car;
import com.example.vts.entity.Report;
import com.example.vts.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {
    CarRepository carRepository;

    public List<Car> getAll() {
        if (carRepository.findAll().size() > 0) {
            return carRepository.findAll();
        }
        return null;
    }

    public Car addCar(Car car) {
        if (carRepository.existsByGovId(car.getGovId())) {
            return null;
        }
        return carRepository.save(car);
    }

    public Car getCarByGovID(String govId) {
        if (carRepository.existsByGovId(govId)) {
            return carRepository.findCarByGovId(govId);
        }
        return null;
    }

    public List<Report> getAllReportsByGovId(String govId) {
        if (carRepository.existsByGovId(govId)) {
            return carRepository.findCarByGovId(govId).getReports();
        }
        return null;
    }

}
