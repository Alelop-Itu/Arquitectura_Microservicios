package com.bank.app.infrastructure.controller;


import com.bank.app.application.dto.MovementDTO;
import com.bank.app.domain.model.Movement;
import com.bank.app.domain.service.MovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
@Slf4j

public class MovementController {

    private final MovementService movementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovementDTO> create(@Valid @RequestBody MovementDTO dto) {
        log.info("Recibiendo solicitud de movimiento para cuenta: {}", dto.getAccountNumber());
        return movementService.createMovement(dto)
                .doOnSuccess(res -> log.info("Movimiento procesado exitosamente"))
                .doOnError(e -> log.error("Error al procesar movimiento: {}", e.getMessage()));
    }

    @GetMapping
    public Flux<MovementDTO> getAll() {
        log.info("Consultando los movimientos");
        return movementService.getAllMovements();
    }

    @PutMapping("/{id}")
    public Mono<MovementDTO> update(@PathVariable Long id, @Valid @RequestBody MovementDTO dto) {
        log.info("Actualizando movimiento con ID: {}", id);
        return movementService.update(id, dto); // Verbo PUT para modificación
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Eliminando movimiento con ID: {}", id);
        return movementService.delete(id);
    }
}
