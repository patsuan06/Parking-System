package com.example.demo.repository;

import com.example.demo.entity.Tariff;
import com.example.demo.entity.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, UUID> {

    Optional<Tariff> findByVehicleType(VehicleType vehicleType);

    boolean existsByVehicleType(VehicleType vehicleType);
}
