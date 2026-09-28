package com.example.demo.controllers;

import com.example.demo.dto.vehicle.VehicleRequest;
import com.example.demo.dto.vehicle.VehicleResponse;
import com.example.demo.services.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
@Tag(name = "Транспортные средства", description = "Управление транспортными средствами")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @Operation(summary = "Зарегистрировать новое транспортное средство")
    public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.create(request));
    }

    @GetMapping
    @Operation(summary = "Получить список всех ТС")
    public ResponseEntity<List<VehicleResponse>> getAll() {
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ТС по ID")
    public ResponseEntity<VehicleResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @GetMapping("/by-plate")
    @Operation(summary = "Найти ТС по госномеру")
    public ResponseEntity<VehicleResponse> getByLicensePlate(@RequestParam String plate) {
        return ResponseEntity.ok(vehicleService.getByLicensePlate(plate));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить данные ТС")
    public ResponseEntity<VehicleResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ТС")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
