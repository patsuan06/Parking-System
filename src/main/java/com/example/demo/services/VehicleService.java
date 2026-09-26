package com.example.demo.services;

import com.example.demo.dto.vehicle.VehicleRequest;
import com.example.demo.dto.vehicle.VehicleResponse;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponse create(VehicleRequest request) {
        if (vehicleRepository.existsByLicensePlate(request.licensePlate())) {
            throw new IllegalArgumentException(
                    "Vehicle with license plate '" + request.licensePlate() + "' already exists");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setVehicleType(request.vehicleType());
        vehicle.setClassification(request.classification());
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setOwner(request.owner());

        return VehicleResponse.from(vehicleRepository.save(vehicle));
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public VehicleResponse getById(UUID id) {
        Vehicle vehicle = findOrThrow(id);
        return VehicleResponse.from(vehicle);
    }

    @Transactional
    public VehicleResponse update(UUID id, VehicleRequest request) {
        Vehicle vehicle = findOrThrow(id);

        // If license plate changed, ensure new one is unique
        if (!vehicle.getLicensePlate().equals(request.licensePlate())
                && vehicleRepository.existsByLicensePlate(request.licensePlate())) {
            throw new IllegalArgumentException(
                    "Vehicle with license plate '" + request.licensePlate() + "' already exists");
        }

        vehicle.setLicensePlate(request.licensePlate());
        vehicle.setVehicleType(request.vehicleType());
        vehicle.setClassification(request.classification());
        vehicle.setBrand(request.brand());
        vehicle.setModel(request.model());
        vehicle.setOwner(request.owner());

        return VehicleResponse.from(vehicleRepository.save(vehicle));
    }

    @Transactional
    public void delete(UUID id) {
        Vehicle vehicle = findOrThrow(id);
        vehicleRepository.delete(vehicle);
    }

    // --- helper ---

    public Vehicle findOrThrow(UUID id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with id: " + id));
    }
}
