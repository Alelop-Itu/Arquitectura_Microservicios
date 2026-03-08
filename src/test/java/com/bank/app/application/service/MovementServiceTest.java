package com.bank.app.application.service;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.common.exception.InsufficientBalanceException;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.domain.repository.MovementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovementServiceTest {

    @Mock
    private MovementRepository movementRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private MovementServiceImpl movementService;

    @Test
    void testCreateDebitWithInsufficientBalanceThrowsException() {
        // 1. Configuración: Cuenta con saldo de 100
        Account account = new Account();
        account.setNumber("478758");
        account.setBalance(new BigDecimal("100.00"));

        MovementDTO dto = new MovementDTO();
        dto.setAccountNumber("478758");
        dto.setType("DEBIT");
        dto.setValue(new BigDecimal("500.00")); // Retiro mayor al saldo

        when(accountRepository.findByNumber("478758")).thenReturn(Optional.of(account));

        // 2. Ejecución y Verificación de la Excepción F3
        StepVerifier.create(movementService.createMovement(dto))
                .expectError(InsufficientBalanceException.class)
                .verify();
    }

    @Test
    void testCreateCreditUpdatesBalanceCorrectly() {
        // 1. Configuración: Cuenta con saldo de 2000
        Account account = new Account();
        account.setNumber("225487");
        account.setBalance(new BigDecimal("2000.00"));

        MovementDTO dto = new MovementDTO();
        dto.setAccountNumber("225487");
        dto.setType("CREDIT");
        dto.setValue(new BigDecimal("600.00"));

        when(accountRepository.findByNumber("225487")).thenReturn(Optional.of(account));
        when(movementRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // 2. Ejecución y Verificación del nuevo saldo (2600)
        StepVerifier.create(movementService.createMovement(dto))
                .assertNext(res -> {
                    assertEquals(new BigDecimal("2600.00"), res.getBalance());
                })
                .verifyComplete();
    }
}
