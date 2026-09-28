package com.example.demo.controllers;

import com.example.demo.dto.tariff.TariffRequest;
import com.example.demo.dto.tariff.TariffResponse;
import com.example.demo.entity.VehicleType;
import com.example.demo.services.TariffService;
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
@RequestMapping("/api/tariffs")
@RequiredArgsConstructor
@Tag(name = "Тарифы", description = "Управление тарифами парковки")
public class TariffController {

    private final TariffService tariffService;

    @PostMapping
    @Operation(summary = "Создать тариф для типа ТС")
    public ResponseEntity<TariffResponse> create(@Valid @RequestBody TariffRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tariffService.create(request));
    }

    @GetMapping
    @Operation(summary = "Получить все тарифы")
    public ResponseEntity<List<TariffResponse>> getAll() {
        return ResponseEntity.ok(tariffService.getAll());
    }

    @GetMapping("/by-type")
    @Operation(summary = "Получить тариф по типу ТС")
    public ResponseEntity<TariffResponse> getByType(@RequestParam VehicleType type) {
        return ResponseEntity.ok(tariffService.getByVehicleType(type));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить тариф")
    public ResponseEntity<TariffResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody TariffRequest request) {
        return ResponseEntity.ok(tariffService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тариф")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        tariffService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
