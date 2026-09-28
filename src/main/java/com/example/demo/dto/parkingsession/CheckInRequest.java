package com.example.demo.dto.parkingsession;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckInRequest(

        @NotBlank(message = "License plate must not be blank")
        @Schema(example = "A-1234-KG")
        String licensePlate,

        @NotNull(message = "Parking spot ID must not be null")
        @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        UUID parkingSpotId
) {}
