package com.example.demo.dto.tariff;

import com.example.demo.entity.Tariff;
import com.example.demo.entity.VehicleType;

import java.util.UUID;

public record TariffResponse(
        UUID id,
        VehicleType vehicleType,
        double ratePerHour
) {
    public static TariffResponse from(Tariff t) {
        return new TariffResponse(t.getId(), t.getVehicleType(), t.getRatePerHour());
    }
}
