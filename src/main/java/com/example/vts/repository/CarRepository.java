package com.example.vts.repository;

import com.example.vts.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findCarByGovId(String govId);

    Boolean existsByGovId(String govId);
}
