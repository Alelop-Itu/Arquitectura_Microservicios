package com.bank.app.infrastructure.controller;


import com.bank.app.application.dto.MovementDTO;
import com.bank.app.application.service.MovementService;
import com.bank.app.common.util.LogMaskingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementService movementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovementDTO> create(@Valid @RequestBody MovementDTO dto) {
        log.info("Recibiendo solicitud de movimiento para cuenta: {}",
                LogMaskingUtil.maskAccountNumber(dto.getAccountNumber()));
        return movementService.createMovement(dto)
                .doOnSuccess(res -> log.info("Movimiento procesado exitosamente, ID: {}", res.getId()))
                .doOnError(e -> log.error("Error al procesar movimiento para cuenta {}: {}",
                        LogMaskingUtil.maskAccountNumber(dto.getAccountNumber()), e.getMessage()));
    }

    @GetMapping
    public Flux<MovementDTO> getAll() {
        log.info("Consultando todos los movimientos");
        return movementService.getAllMovements();
    }

    @PutMapping("/{id}")
    public Mono<MovementDTO> update(@PathVariable Long id, @Valid @RequestBody MovementDTO dto) {
        log.info("Actualizando movimiento con ID: {}", id);
        return movementService.update(id, dto)
                .doOnSuccess(res -> log.info("Movimiento {} actualizado exitosamente", id))
                .doOnError(e -> log.error("Error al actualizar movimiento {}: {}", id, e.getMessage()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Eliminando movimiento con ID: {}", id);
        return movementService.delete(id)
                .doOnSuccess(v -> log.info("Movimiento {} eliminado exitosamente", id))
                .doOnError(e -> log.error("Error al eliminar movimiento {}: {}", id, e.getMessage()));
    }

    @GetMapping("/filter")
    public Flux<MovementDTO> getFilteredMovements(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Filtrando movimientos de cuenta {} entre {} y {}",
                LogMaskingUtil.maskAccountNumber(accountNumber), startDate, endDate);
        return movementService.getMovementsByAccountAndDates(accountNumber, startDate, endDate);
    }
}