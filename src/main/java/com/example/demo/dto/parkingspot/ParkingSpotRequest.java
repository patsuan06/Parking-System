package com.example.demo.dto.parkingspot;

import com.example.demo.entity.Classification;
import com.example.demo.entity.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ParkingSpotRequest(

        @Min(value = 1, message = "Spot number must be positive")
        @Schema(example = "1")
        int spotNumber,

        @NotNull(message = "Vehicle type must not be null")
        @Schema(example = "CAR")
        VehicleType type,

        @NotNull(message = "Classification must not be null")
        @Schema(example = "ICE")
        Classification classification
) {}
