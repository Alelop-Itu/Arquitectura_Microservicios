package com.bank.app.infrastructure.controller;

import com.bank.app.application.dto.CustomerDTO;
import com.bank.app.application.service.CustomerService;
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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CustomerDTO> create(@Valid @RequestBody CustomerDTO dto) {
        log.info("Creando cliente: {}", LogMaskingUtil.maskName(dto.getName()));
        return customerService.save(dto)
                .doOnSuccess(res -> log.info("Cliente creado exitosamente con ID: {}", res.getId()))
                .doOnError(e -> log.error("Error al crear cliente {}: {}",
                        LogMaskingUtil.maskName(dto.getName()), e.getMessage()));
    }

    @GetMapping
    public Flux<CustomerDTO> getAll() {
        log.info("Consultando todos los clientes");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<CustomerDTO> getById(@PathVariable Long id) {
        log.info("Consultando cliente con ID: {}", id);
        return customerService.findById(id)
                .doOnError(e -> log.error("Error al consultar cliente {}: {}", id, e.getMessage()));
    }

    @PutMapping("/{id}")
    public Mono<CustomerDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO dto) {
        log.info("Actualizando cliente con ID: {}", id);
        return customerService.update(id, dto)
                .doOnSuccess(res -> log.info("Cliente {} actualizado exitosamente", id))
                .doOnError(e -> log.error("Error al actualizar cliente {}: {}", id, e.getMessage()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        log.info("Eliminando cliente con ID: {}", id);
        return customerService.delete(id)
                .doOnSuccess(v -> log.info("Cliente {} eliminado exitosamente", id))
                .doOnError(e -> log.error("Error al eliminar cliente {}: {}", id, e.getMessage()));
    }
}