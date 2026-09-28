package com.example.demo.controllers;

import com.example.demo.dto.parkingsession.CheckInRequest;
import com.example.demo.dto.parkingsession.ParkingSessionResponse;
import com.example.demo.services.ParkingSessionService;
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
@RequestMapping("/api/parking-sessions")
@RequiredArgsConstructor
@Tag(name = "Сессии", description = "Въезд и выезд транспортных средств")
public class ParkingSessionController {

    private final ParkingSessionService sessionService;

    @PostMapping("/check-in")
    @Operation(summary = "Въезд — поставить ТС на парковочное место")
    public ResponseEntity<ParkingSessionResponse> checkIn(@Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.checkIn(request));
    }

    @PostMapping("/check-out/{sessionId}")
    @Operation(summary = "Выезд — рассчитать стоимость и освободить место")
    public ResponseEntity<ParkingSessionResponse> checkOut(@PathVariable UUID sessionId) {
        return ResponseEntity.ok(sessionService.checkOut(sessionId));
    }

    @GetMapping
    @Operation(summary = "Получить все сессии")
    public ResponseEntity<List<ParkingSessionResponse>> getAll() {
        return ResponseEntity.ok(sessionService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить сессию по ID")
    public ResponseEntity<ParkingSessionResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(sessionService.getById(id));
    }

    @GetMapping("/active")
    @Operation(summary = "Получить активные сессии (ТС ещё на парковке)")
    public ResponseEntity<List<ParkingSessionResponse>> getActive() {
        return ResponseEntity.ok(sessionService.getActive());
    }
}
