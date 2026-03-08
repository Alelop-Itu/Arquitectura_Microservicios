package com.bank.app.domain.service;


import com.bank.app.application.dto.MovementDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface MovementService {
    Mono<MovementDTO> createMovement(MovementDTO movementDto);

    Flux<MovementDTO> getAllMovements();

    Mono<MovementDTO> findById(Long id);

    Mono<MovementDTO> update(Long id, MovementDTO dto);

    Mono<Void> delete(Long id);

    Flux<MovementDTO> getMovementsByAccountAndDates(String accountNumber, LocalDateTime start, LocalDateTime end);
}