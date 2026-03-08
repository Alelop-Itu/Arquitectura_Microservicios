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
    @Transactional
    public Mono<MovementDTO> createMovement(MovementDTO dto) {
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findByNumber(dto.getAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

            BigDecimal currentBalance = account.getBalance();
            BigDecimal amount = dto.getValue();
            BigDecimal newBalance;


            if ("DEBIT".equalsIgnoreCase(dto.getType())) {
                newBalance = currentBalance.subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new InsufficientBalanceException("Saldo no disponible");
                }
            } else {
                newBalance = currentBalance.add(amount);
            }


            account.setBalance(newBalance);
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
                    dto.setId(m.getId());
                    dto.setDate(m.getDate());
                    dto.setType(m.getType());
                    dto.setValue(m.getValue());
                    dto.setBalance(m.getBalance());
                    dto.setAccountNumber(m.getAccount().getNumber());
                    return dto;
                }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MovementDTO> findById(Long id) {
        return Mono.fromCallable(() -> movementRepository.findById(id))
                .flatMap(opt -> opt.map(m -> Mono.just(new MovementDTO(m.getId(), m.getDate(), m.getType(), m.getValue(), m.getBalance(), m.getAccount().getNumber())))
                        .orElse(Mono.empty()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MovementDTO> update(Long id, MovementDTO dto) {
        return Mono.fromCallable(() -> {

            Movement movement = movementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
            Account account = movement.getAccount();
            BigDecimal currentBalance = account.getBalance();


            if ("DEBIT".equalsIgnoreCase(movement.getType())) {
                currentBalance = currentBalance.add(movement.getValue());
            } else {
                currentBalance = currentBalance.subtract(movement.getValue());
            }

            BigDecimal newValue = dto.getValue();
            if ("DEBIT".equalsIgnoreCase(dto.getType())) {
                if (currentBalance.compareTo(newValue) < 0) {
                    throw new InsufficientBalanceException("Saldo no disponible"); // Validación F3
                }
                currentBalance = currentBalance.subtract(newValue);
            } else {
                currentBalance = currentBalance.add(newValue);
            }

            account.setBalance(currentBalance);
            accountRepository.save(account);

            movement.setType(dto.getType());
            movement.setValue(newValue);
            movement.setBalance(currentBalance);
            movementRepository.save(movement);

            dto.setBalance(currentBalance);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> movementRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic()).then();
    }
}

