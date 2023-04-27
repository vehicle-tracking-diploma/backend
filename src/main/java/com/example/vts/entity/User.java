package com.example.vts.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles = new ArrayList<>();
    @OneToMany
    private List<Car> cars = new ArrayList<>();
}