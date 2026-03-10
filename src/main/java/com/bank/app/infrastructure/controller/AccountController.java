package com.bank.app.infrastructure.controller;

import com.bank.app.application.dto.AccountDTO;
import com.bank.app.domain.service.AccountService;
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
        return accountService.save(dto);
    }

    @GetMapping("/{number}")
    public Mono<AccountDTO> getByNumber(@PathVariable String number) {
        return accountService.findByNumber(number);
    }

    @PutMapping("/{number}")
    public Mono<AccountDTO> update(@PathVariable String number, @Valid @RequestBody AccountDTO dto) {
        return accountService.update(number, dto);
    }

    @DeleteMapping("/{number}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String number) {
        return accountService.delete(number);
    }

    @GetMapping
    public Flux<AccountDTO> getAll() {
        return accountService.findAll();
    }
}
