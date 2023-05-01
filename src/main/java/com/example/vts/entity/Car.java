package com.example.vts.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "car")
@Data
public class Car {
    @Id
    private String govId;
    @OneToMany
    private List<Report> reports = new ArrayList<>();
}
