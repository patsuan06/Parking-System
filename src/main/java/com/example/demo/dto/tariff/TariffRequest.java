package com.example.demo.dto.tariff;

import com.example.demo.entity.VehicleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TariffRequest(

        @NotNull(message = "Vehicle type must not be null")
        @Schema(example = "CAR")
        VehicleType vehicleType,

        @Positive(message = "Rate per hour must be positive")
        @Schema(example = "100.0")
        double ratePerHour
) {}
