package com.bank.app.infrastructure.controller;


import com.bank.app.application.dto.MovementDTO;
import com.bank.app.domain.model.Movement;
import com.bank.app.domain.service.MovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
@Slf4j

public class MovementController {
    private final MovementService movementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovementDTO> create(@RequestBody MovementDTO dto) {
        log.info("Petición de movimiento recibida para la cuenta: {}", dto.getAccountNumber());
        return movementService.createMovement(dto);
    }

    @GetMapping
    public Flux<MovementDTO> getAll() {
        log.info("Consultando los movimientos");
        return movementService.getAllMovements();
    }
}
