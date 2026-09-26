package com.example.demo.entity;
import java.util.UUID;
import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "classification")
    private Classification classification;

    @Column(name = "owner")
    private String owner;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

}
