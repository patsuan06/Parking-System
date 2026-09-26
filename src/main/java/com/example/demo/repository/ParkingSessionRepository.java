package com.example.demo.repository;

import com.example.demo.entity.ParkingSession;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingSessionRepository extends JpaRepository<ParkingSession, UUID> {

    // Active session = endTime is null
    Optional<ParkingSession> findByVehicleAndEndTimeIsNull(Vehicle vehicle);

    Optional<ParkingSession> findByParkingSpotAndEndTimeIsNull(ParkingSpot parkingSpot);

    List<ParkingSession> findByEndTimeIsNull();

    List<ParkingSession> findByVehicle(Vehicle vehicle);
}
