package com.example.vts.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String originLng;
    private String originLat;
    private String destLng;
    private String destLat;
}
