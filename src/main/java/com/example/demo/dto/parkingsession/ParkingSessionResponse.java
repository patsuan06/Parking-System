package com.example.demo.dto.parkingsession;

import com.example.demo.dto.parkingspot.ParkingSpotResponse;
import com.example.demo.dto.vehicle.VehicleResponse;
import com.example.demo.entity.ParkingSession;

import java.time.LocalDateTime;
import java.util.UUID;

public record ParkingSessionResponse(
        UUID id,
        VehicleResponse vehicle,
        ParkingSpotResponse parkingSpot,
        LocalDateTime startTime,
        LocalDateTime endTime,
        double price
) {
    public static ParkingSessionResponse from(ParkingSession s) {
        return new ParkingSessionResponse(
                s.getId(),
                VehicleResponse.from(s.getVehicle()),
                ParkingSpotResponse.from(s.getParkingSpot()),
                s.getStartTime(),
                s.getEndTime(),
                s.getPrice()
        );
    }
}
