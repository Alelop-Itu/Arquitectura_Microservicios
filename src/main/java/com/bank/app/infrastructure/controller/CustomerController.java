package com.bank.app.infrastructure.controller;

import com.bank.app.application.dto.CustomerDTO;
import com.bank.app.domain.service.CustomerService;
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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDTO> create(@Valid @RequestBody CustomerDTO dto) {
        return customerService.save(dto);
    }

    @GetMapping
    public Flux<CustomerDTO> getAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> getById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<CustomerDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        return customerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        return customerService.delete(id);
    }
}
