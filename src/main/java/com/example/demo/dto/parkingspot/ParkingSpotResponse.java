package com.example.demo.dto.parkingspot;

import com.example.demo.entity.Classification;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.VehicleType;

import java.util.UUID;

public record ParkingSpotResponse(
        UUID id,
        int spotNumber,
        VehicleType type,
        Classification classification,
        boolean isOccupied
) {
    public static ParkingSpotResponse from(ParkingSpot s) {
        return new ParkingSpotResponse(
                s.getId(),
                s.getSpotNumber(),
                s.getType(),
                s.getClassification(),
                s.isOccupied()
        );
    }
}
