package com.example.demo.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.parkingspot.ParkingSpotRequest;
import com.example.demo.dto.parkingspot.ParkingSpotResponse;
import com.example.demo.entity.Classification;
import com.example.demo.entity.ParkingSpot;
import com.example.demo.entity.VehicleType;
import com.example.demo.repository.ParkingSpotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;

    @Transactional
    public ParkingSpotResponse create(ParkingSpotRequest request) {
        if (parkingSpotRepository.existsBySpotNumber(request.spotNumber())) {
            throw new IllegalArgumentException(
                    "Parking spot with number " + request.spotNumber() + " already exists");
        }

        ParkingSpot spot = new ParkingSpot();
        spot.setSpotNumber(request.spotNumber());
        spot.setType(request.type());
        spot.setClassification(request.classification());
        spot.setOccupied(false);

        return ParkingSpotResponse.from(parkingSpotRepository.save(spot));
    }

    public List<ParkingSpotResponse> getAll() {
        return parkingSpotRepository.findAll()
                .stream()
                .map(ParkingSpotResponse::from)
                .toList();
    }

    public ParkingSpotResponse getById(UUID id) {
        return ParkingSpotResponse.from(findOrThrow(id));
    }

    @Transactional
    public ParkingSpotResponse update(UUID id, ParkingSpotRequest request) {
        ParkingSpot spot = findOrThrow(id);

        if (spot.getSpotNumber() != request.spotNumber()
                && parkingSpotRepository.existsBySpotNumber(request.spotNumber())) {
            throw new IllegalArgumentException(
                    "Parking spot with number " + request.spotNumber() + " already exists");
        }

        spot.setSpotNumber(request.spotNumber());
        spot.setType(request.type());
        spot.setClassification(request.classification());

        return ParkingSpotResponse.from(parkingSpotRepository.save(spot));
    }

    @Transactional
    public void delete(UUID id) {
        ParkingSpot spot = findOrThrow(id);
        if (spot.isOccupied()) {
            throw new IllegalStateException(
                    "Cannot delete spot " + spot.getSpotNumber() + " while it is occupied");
        }
        parkingSpotRepository.delete(spot);
    }

    public List<ParkingSpotResponse> getAvailable(VehicleType type, Classification classification) {
        if (type != null && classification != null) {
            return parkingSpotRepository.findByIsOccupiedAndTypeAndClassification(false, type, classification)
                    .stream().map(ParkingSpotResponse::from).toList();
        } else if (type != null) {
            return parkingSpotRepository.findByIsOccupiedAndType(false, type)
                    .stream().map(ParkingSpotResponse::from).toList();
        } else if (classification != null) {
            return parkingSpotRepository.findByIsOccupiedAndClassification(false, classification)
                    .stream().map(ParkingSpotResponse::from).toList();
        }
        return parkingSpotRepository.findByIsOccupied(false)
                .stream().map(ParkingSpotResponse::from).toList();
    }

    // --- helpers ---

    public ParkingSpot findOrThrow(UUID id) {
        return parkingSpotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found with id: " + id));
    }
    
    public ParkingSpot findOrThrowLocked(UUID id) {
        return parkingSpotRepository.findByIdWithLock(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found with id: " + id));
    }
}
