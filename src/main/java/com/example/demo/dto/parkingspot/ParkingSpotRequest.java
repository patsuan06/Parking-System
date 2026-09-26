package com.example.demo.dto.parkingspot;

import com.example.demo.entity.Classification;
import com.example.demo.entity.VehicleType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParkingSpotRequest(

        @Min(value = 1, message = "Spot number must be positive")
        int spotNumber,

        @NotNull(message = "Vehicle type must not be null")
        VehicleType type,

        @NotNull(message = "Classification must not be null")
        Classification classification
) {}
