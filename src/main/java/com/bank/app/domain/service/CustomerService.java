package com.bank.app.domain.service;

import com.bank.app.application.dto.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDTO> save(CustomerDTO dto);

    Flux<CustomerDTO> findAll();

    Mono<CustomerDTO> findById(Long id);

    Mono<CustomerDTO> update(Long id, CustomerDTO dto);

    Mono<Void> delete(Long id);
}
