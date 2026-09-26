package com.example.demo.repository;

import com.example.demo.entity.Classification;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.VehicleType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    boolean existsBySpotNumber(int spotNumber);

    List<ParkingSpot> findByIsOccupied(boolean isOccupied);

    List<ParkingSpot> findByIsOccupiedAndType(boolean isOccupied, VehicleType type);

    List<ParkingSpot> findByIsOccupiedAndClassification(boolean isOccupied, Classification classification);

    List<ParkingSpot> findByIsOccupiedAndTypeAndClassification(boolean isOccupied, VehicleType type, Classification classification);
}
