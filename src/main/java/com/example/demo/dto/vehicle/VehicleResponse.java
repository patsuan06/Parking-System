package com.example.demo.dto.vehicle;

import com.example.demo.entity.Classification;
import com.example.demo.entity.Vehicle;
import com.example.demo.entity.VehicleType;

import java.util.UUID;  

public record VehicleResponse(
        UUID id,
        String licensePlate,
        VehicleType vehicleType,
        Classification classification,
        String brand,
        String model,
        String owner
) {
    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(
                v.getId(),
                v.getLicensePlate(),
                v.getVehicleType(),
                v.getClassification(),
                v.getBrand(),
                v.getModel(),
                v.getOwner()
        );
    }
}
