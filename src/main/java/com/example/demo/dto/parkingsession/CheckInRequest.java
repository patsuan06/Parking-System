package com.example.demo.dto.parkingsession;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CheckInRequest(

        @NotNull(message = "Vehicle ID must not be null")
        UUID vehicleId,

        @NotNull(message = "Parking spot ID must not be null")
        UUID parkingSpotId
) {}
