package com.bank.app.application.service.impl;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.common.constant.MovementType;
import com.bank.app.common.exception.AccountNotFoundException;
import com.bank.app.common.exception.InsufficientBalanceException;
import com.bank.app.common.exception.MovementNotFoundException;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.Movement;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.domain.repository.MovementRepository;
import com.bank.app.infrastructure.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MovementTransactionalExecutor {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public MovementDTO createMovement(MovementDTO dto) {
        Account account = accountRepository.findByNumber(dto.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(dto.getAccountNumber()));

        BigDecimal currentBalance = account.getBalance();
        BigDecimal amount = dto.getValue();
        BigDecimal newBalance;

        if (MovementType.esDebito(dto.getType())) {
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

        Movement saved = movementRepository.save(movement);
        return MovementMapper.toDto(saved);
    }

    @Transactional
    public MovementDTO updateMovement(Long id, MovementDTO dto) {
        Movement movement = movementRepository.findById(id)
                .orElseThrow(() -> new MovementNotFoundException(id));
        Account account = movement.getAccount();
        BigDecimal currentBalance = account.getBalance();

        // Revierte el efecto del movimiento anterior
        if (MovementType.esDebito(movement.getType())) {
            currentBalance = currentBalance.add(movement.getValue());
        } else {
            currentBalance = currentBalance.subtract(movement.getValue());
        }

        // Aplica el nuevo valor
        BigDecimal newValue = dto.getValue();
        if (MovementType.esDebito(dto.getType())) {
            if (currentBalance.compareTo(newValue) < 0) {
                throw new InsufficientBalanceException("Saldo no disponible");
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
        Movement updated = movementRepository.save(movement);

        return MovementMapper.toDto(updated);
    }
}
