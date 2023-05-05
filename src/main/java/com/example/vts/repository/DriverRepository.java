package com.example.vts.repository;

import com.example.vts.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Boolean existsByPhone(String phone);
}
