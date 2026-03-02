package com.bank.app.domain.service;


import com.bank.app.application.dto.MovementDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MovementService {
    Mono<MovementDTO> createMovement(MovementDTO movementDto);
    Flux<MovementDTO> getAllMovements();
}