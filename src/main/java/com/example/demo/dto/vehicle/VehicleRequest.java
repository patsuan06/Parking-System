package com.example.demo.dto.vehicle;

import com.example.demo.entity.Classification;
import com.example.demo.entity.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VehicleRequest(

        @NotBlank(message = "License plate must not be blank")
        @Schema(example = "A-1234-KG")
        String licensePlate,

        @NotNull(message = "Vehicle type must not be null")
        @Schema(example = "CAR")
        VehicleType vehicleType,

        @NotNull(message = "Classification must not be null")
        @Schema(example = "ICE")
        Classification classification,

        @NotBlank(message = "Brand must not be blank")
        @Schema(example = "Toyota")
        String brand,

        @NotBlank(message = "Model must not be blank")
        @Schema(example = "Camry")
        String model,

        @Schema(example = "John Doe")
        String owner
) {}
