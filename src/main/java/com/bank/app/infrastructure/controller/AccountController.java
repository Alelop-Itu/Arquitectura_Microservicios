package com.bank.app.infrastructure.controller;

import com.bank.app.application.dto.AccountDTO;
import com.bank.app.application.service.AccountService;
import com.bank.app.common.util.LogMaskingUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDTO> create(@Valid @RequestBody AccountDTO dto) {
        log.info("Creando cuenta número: {} para cliente ID: {}",
                LogMaskingUtil.maskAccountNumber(dto.getNumber()), dto.getCustomerId());
        return accountService.save(dto)
                .doOnSuccess(res -> log.info("Cuenta creada exitosamente: {}",
                        LogMaskingUtil.maskAccountNumber(res.getNumber())))
                .doOnError(e -> log.error("Error al crear cuenta {}: {}",
                        LogMaskingUtil.maskAccountNumber(dto.getNumber()), e.getMessage()));
    }

    @GetMapping("/{number}")
    public Mono<AccountDTO> getByNumber(@PathVariable String number) {
        log.info("Consultando cuenta número: {}", LogMaskingUtil.maskAccountNumber(number));
        return accountService.findByNumber(number)
                .doOnError(e -> log.error("Error al consultar cuenta {}: {}",
                        LogMaskingUtil.maskAccountNumber(number), e.getMessage()));
    }

    @PutMapping("/{number}")
    public Mono<AccountDTO> update(@PathVariable String number, @Valid @RequestBody AccountDTO dto) {
        log.info("Actualizando cuenta número: {}", LogMaskingUtil.maskAccountNumber(number));
        return accountService.update(number, dto)
                .doOnSuccess(res -> log.info("Cuenta {} actualizada exitosamente",
                        LogMaskingUtil.maskAccountNumber(number)))
                .doOnError(e -> log.error("Error al actualizar cuenta {}: {}",
                        LogMaskingUtil.maskAccountNumber(number), e.getMessage()));
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String number) {
        log.info("Eliminando cuenta número: {}", LogMaskingUtil.maskAccountNumber(number));
        return accountService.delete(number)
                .doOnSuccess(v -> log.info("Cuenta {} eliminada exitosamente",
                        LogMaskingUtil.maskAccountNumber(number)))
                .doOnError(e -> log.error("Error al eliminar cuenta {}: {}",
                        LogMaskingUtil.maskAccountNumber(number), e.getMessage()));
    }

    @GetMapping
    public Flux<AccountDTO> getAll() {
        log.info("Consultando todas las cuentas");
        return accountService.findAll();
    }
}
