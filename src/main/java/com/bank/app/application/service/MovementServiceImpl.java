package com.bank.app.application.service;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.common.exception.InsufficientBalanceException;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.Movement;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.domain.repository.MovementRepository;
import com.bank.app.domain.service.MovementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional // Asegura que si algo falla, no se guarde nada a medias (F2)
    public Mono<MovementDTO> createMovement(MovementDTO dto) {
        return Mono.fromCallable(() -> {
            log.info("Procesando {} de {} para cuenta: {}", dto.getType(), dto.getValue(), dto.getAccountNumber());


            Account account = accountRepository.findByNumber(dto.getAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

            BigDecimal currentBalance = account.getInitialBalance();
            BigDecimal amount = dto.getValue();
            BigDecimal newBalance;


            if ("RETIRAR".equalsIgnoreCase(dto.getType())) {
                newBalance = currentBalance.subtract(amount);


                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    log.warn("Saldo insuficiente en cuenta {}", dto.getAccountNumber());
                    throw new InsufficientBalanceException();
                }
            } else {
                newBalance = currentBalance.add(amount);
            }


            account.setInitialBalance(newBalance);
            accountRepository.save(account);


            Movement movement = new Movement();
            movement.setDate(LocalDateTime.now());
            movement.setType(dto.getType());
            movement.setValue(amount);
            movement.setBalance(newBalance);
            movement.setAccount(account);
            movementRepository.save(movement);

            dto.setBalance(newBalance);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<MovementDTO> getAllMovements() {
        return Flux.defer(() -> Flux.fromIterable(movementRepository.findAll()))
                .map(m -> {
                    MovementDTO dto = new MovementDTO();
                    dto.setAccountNumber(m.getAccount().getNumber());
                    dto.setType(m.getType());
                    dto.setValue(m.getValue());
                    dto.setBalance(m.getBalance());
                    return dto;
                }).subscribeOn(Schedulers.boundedElastic());
    }
}