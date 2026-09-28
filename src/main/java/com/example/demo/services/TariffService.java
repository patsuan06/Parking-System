package com.example.demo.services;

import com.example.demo.dto.tariff.TariffRequest;
import com.example.demo.dto.tariff.TariffResponse;
import com.example.demo.entity.Tariff;
import com.example.demo.entity.VehicleType;
import com.example.demo.repository.TariffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TariffService {

    private final TariffRepository tariffRepository;

    @Transactional
    public TariffResponse create(TariffRequest request) {
        if (tariffRepository.existsByVehicleType(request.vehicleType())) {
            throw new IllegalArgumentException(
                    "Tariff for vehicle type " + request.vehicleType() + " already exists");
        }

        Tariff tariff = new Tariff();
        tariff.setVehicleType(request.vehicleType());
        tariff.setRatePerHour(request.ratePerHour());

        return TariffResponse.from(tariffRepository.save(tariff));
    }

    public List<TariffResponse> getAll() {
        return tariffRepository.findAll().stream().map(TariffResponse::from).toList();
    }

    public TariffResponse getByVehicleType(VehicleType type) {
        return TariffResponse.from(findByTypeOrThrow(type));
    }

    @Transactional
    public TariffResponse update(UUID id, TariffRequest request) {
        Tariff tariff = findOrThrow(id);

        // If vehicle type changed, ensure no duplicate
        if (tariff.getVehicleType() != request.vehicleType()
                && tariffRepository.existsByVehicleType(request.vehicleType())) {
            throw new IllegalArgumentException(
                    "Tariff for vehicle type " + request.vehicleType() + " already exists");
        }

        tariff.setVehicleType(request.vehicleType());
        tariff.setRatePerHour(request.ratePerHour());

        return TariffResponse.from(tariffRepository.save(tariff));
    }

    @Transactional
    public void delete(UUID id) {
        tariffRepository.delete(findOrThrow(id));
    }

    // --- helpers ---

    public double getRateOrThrow(VehicleType type) {
        return findByTypeOrThrow(type).getRatePerHour();
    }

    private Tariff findOrThrow(UUID id) {
        return tariffRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tariff not found with id: " + id));
    }

    private Tariff findByTypeOrThrow(VehicleType type) {
        return tariffRepository.findByVehicleType(type)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No tariff configured for vehicle type: " + type));
    }
}
