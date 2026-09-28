package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.parkingsession.CheckInRequest;
import com.example.demo.dto.parkingsession.ParkingSessionResponse;
import com.example.demo.entity.ParkingSession;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.Vehicle;
import com.example.demo.repository.ParkingSessionRepository;
import com.example.demo.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingSessionService {


    private final ParkingSessionRepository sessionRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotService spotService;
    private final TariffService tariffService;

    @Transactional
    public ParkingSessionResponse checkIn(CheckInRequest request) {
        Vehicle vehicle = vehicleRepository.findByLicensePlate(request.licensePlate())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Vehicle not found with license plate: " + request.licensePlate()));
        // Locks the spot row (SELECT ... FOR UPDATE) so two simultaneous
        // check-ins for the same spot are serialized at the DB level.
        ParkingSpot spot = spotService.findOrThrowLocked(request.parkingSpotId());

        // 1. Check spot is free
        if (spot.isOccupied()) {
            throw new IllegalStateException(
                    "Parking spot " + spot.getSpotNumber() + " is already occupied");
        }

        // 2. Check vehicle type compatibility
        if (vehicle.getVehicleType() != spot.getType()) {
            throw new IllegalArgumentException(
                    "Vehicle type " + vehicle.getVehicleType()
                            + " is not compatible with spot type " + spot.getType());
        }

        // 3. Check vehicle is not already parked somewhere
        sessionRepository.findByVehicleAndEndTimeIsNull(vehicle).ifPresent(s -> {
            throw new IllegalStateException(
                    "Vehicle " + vehicle.getLicensePlate() + " is already parked in spot "
                            + s.getParkingSpot().getSpotNumber());
        });

        // 4. Create session
        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setParkingSpot(spot);
        session.setStartTime(LocalDateTime.now());

        // 5. Mark spot occupied
        spot.setOccupied(true);

        return ParkingSessionResponse.from(sessionRepository.save(session));
    }

    @Transactional
    public ParkingSessionResponse checkOut(UUID sessionId) {
        ParkingSession session = findOrThrow(sessionId);

        if (session.getEndTime() != null) {
            throw new IllegalStateException("Session " + sessionId + " is already closed");
        }

        LocalDateTime endTime = LocalDateTime.now();
        session.setEndTime(endTime);
        session.setPrice(calculatePrice(session.getVehicle(), session.getStartTime(), endTime));

        // Free the spot
        session.getParkingSpot().setOccupied(false);

        return ParkingSessionResponse.from(sessionRepository.save(session));
    }

    public List<ParkingSessionResponse> getAll() {
        return sessionRepository.findAll()
                .stream()
                .map(ParkingSessionResponse::from)
                .toList();
    }

    public ParkingSessionResponse getById(UUID id) {
        return ParkingSessionResponse.from(findOrThrow(id));
    }

    public List<ParkingSessionResponse> getActive() {
        return sessionRepository.findByEndTimeIsNull()
                .stream()
                .map(ParkingSessionResponse::from)
                .toList();
    }

    // --- helpers ---

    private ParkingSession findOrThrow(UUID id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking session not found with id: " + id));
    }

    private double calculatePrice(Vehicle vehicle, LocalDateTime start, LocalDateTime end) {
        long minutes = ChronoUnit.MINUTES.between(start, end);
        // Round up to next full hour, minimum 1 hour
        long hours = Math.max(1, (long) Math.ceil(minutes / 60.0));
        double rate = tariffService.getRateOrThrow(vehicle.getVehicleType());
        return hours * rate;
    }
}
