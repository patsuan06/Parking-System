package com.example.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Classification;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.VehicleType;

import jakarta.persistence.LockModeType;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, UUID> {

    boolean existsBySpotNumber(int spotNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ParkingSpot s WHERE s.id = :id")
    Optional<ParkingSpot> findByIdWithLock(UUID id);

    List<ParkingSpot> findByIsOccupied(boolean isOccupied);

    List<ParkingSpot> findByIsOccupiedAndType(boolean isOccupied, VehicleType type);

    List<ParkingSpot> findByIsOccupiedAndClassification(boolean isOccupied, Classification classification);

    List<ParkingSpot> findByIsOccupiedAndTypeAndClassification(boolean isOccupied, VehicleType type, Classification classification);
}
