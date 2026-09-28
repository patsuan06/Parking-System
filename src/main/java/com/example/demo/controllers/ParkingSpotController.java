package com.example.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "Парковочные места", description = "Управление парковочными местами")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    @Operation(summary = "Создать новое парковочное место")
    public ResponseEntity<ParkingSpotResponse> create(@Valid @RequestBody ParkingSpotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.create(request));
    }

    @GetMapping
    @Operation(summary = "Получить список всех парковочных мест")
    public ResponseEntity<List<ParkingSpotResponse>> getAll() {
        return ResponseEntity.ok(parkingSpotService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить парковочное место по ID")
    public ResponseEntity<ParkingSpotResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(parkingSpotService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить парковочное место")
    public ResponseEntity<ParkingSpotResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ParkingSpotRequest request) {
        return ResponseEntity.ok(parkingSpotService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить парковочное место")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        parkingSpotService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    @Operation(summary = "Получить свободные места (с фильтрацией по типу и классификации)")
    public ResponseEntity<List<ParkingSpotResponse>> getAvailable(
            @RequestParam(required = false) VehicleType type,
            @RequestParam(required = false) Classification classification) {
        return ResponseEntity.ok(parkingSpotService.getAvailable(type, classification));
    }
}
