package com.bank.app.application.service.impl;

import com.bank.app.application.dto.CustomerDTO;
import com.bank.app.application.service.CustomerService;
import com.bank.app.common.exception.CustomerNotFoundException;
import com.bank.app.domain.model.Customer;
import com.bank.app.domain.repository.CustomerRepository;
import com.bank.app.infrastructure.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerDTO> save(CustomerDTO dto) {
        return Mono.fromCallable(() -> {
            Customer customer = CustomerMapper.toDomain(dto);
            Customer saved = customerRepository.save(customer);
            return CustomerMapper.toDto(saved);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<CustomerDTO> findAll() {
        return Mono.fromCallable(customerRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .map(CustomerMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<CustomerDTO> findById(Long id) {
        return Mono.fromCallable(() -> customerRepository.findById(id)
                        .orElseThrow(() -> new CustomerNotFoundException(id)))
                .map(CustomerMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<CustomerDTO> update(Long id, CustomerDTO dto) {
        return Mono.fromCallable(() -> {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException(id));
            customer.setName(dto.getName());
            customer.setAddress(dto.getAddress());
            customer.setPhone(dto.getPhone());
            customer.setStatus(dto.getStatus());
            Customer updated = customerRepository.save(customer);
            return CustomerMapper.toDto(updated);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromCallable(() -> customerRepository.findById(id)
                        .orElseThrow(() -> new CustomerNotFoundException(id)))
                .doOnNext(customer -> customerRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}