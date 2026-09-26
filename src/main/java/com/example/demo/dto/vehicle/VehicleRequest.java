package com.example.demo.dto.vehicle;
import com.example.demo.entity.Classification;
import com.example.demo.entity.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(

        @NotBlank(message = "License plate must not be blank")
        String licensePlate,

        @NotNull(message = "Vehicle type must not be null")
        VehicleType vehicleType,

        @NotNull(message = "Classification must not be null")
        Classification classification,

        @NotBlank(message = "Brand must not be blank")
        String brand,

        @NotBlank(message = "Model must not be blank")
        String model,

        String owner
) {}
