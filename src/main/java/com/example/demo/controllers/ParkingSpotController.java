package com.example.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.parkingspot.ParkingSpotRequest;
import com.example.demo.dto.parkingspot.ParkingSpotResponse;
import com.example.demo.entity.Classification;
import com.example.demo.entity.VehicleType;
import com.example.demo.services.ParkingSpotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parking-spots")
@RequiredArgsConstructor
@Tag(name = "Parking Spots", description = "Parking spot management")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    @Operation(summary = "Create a new parking spot")
    public ResponseEntity<ParkingSpotResponse> create(@Valid @RequestBody ParkingSpotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.create(request));
    }

    @GetMapping
    @Operation(summary = "Get all parking spots")
    public ResponseEntity<List<ParkingSpotResponse>> getAll() {
        return ResponseEntity.ok(parkingSpotService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parking spot by ID")
    public ResponseEntity<ParkingSpotResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(parkingSpotService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update parking spot")
    public ResponseEntity<ParkingSpotResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ParkingSpotRequest request) {
        return ResponseEntity.ok(parkingSpotService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete parking spot")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        parkingSpotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    @Operation(summary = "Get available (free) parking spots, with optional filtering by type and classification")
    public ResponseEntity<List<ParkingSpotResponse>> getAvailable(
            @RequestParam(required = false) VehicleType type,
            @RequestParam(required = false) Classification classification) {
        return ResponseEntity.ok(parkingSpotService.getAvailable(type, classification));
    }
}
