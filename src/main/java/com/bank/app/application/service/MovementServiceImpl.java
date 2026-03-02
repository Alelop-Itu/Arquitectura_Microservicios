package com.bank.app.application.service;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.common.exception.InsufficientBalanceException;
import com.bank.app.domain.service.MovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@Slf4j
public class MovementServiceImpl implements MovementService {

    @Override
    public Mono<MovementDTO> createMovement(MovementDTO dto) {
        log.info("Iniciando movimiento para la cuenta: {}" ,dto.getAccountNumber());
        if (dto.getValue().compareTo(BigDecimal.ZERO) < 0) {
           log.error("Error: Intento de retiro con saldo insuficiente");
           return Mono.error(new InsufficientBalanceException());
        }
        log.info("Movimiento procesado con éxito");
        return Mono.just(dto);
    }

    @Override
    public Flux<MovementDTO> getAllMovements() {
        return Flux.empty();
    }

}
