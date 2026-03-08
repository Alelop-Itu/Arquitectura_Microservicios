package com.bank.app.application.service;

import com.bank.app.application.dto.CustomerDTO;
import com.bank.app.domain.model.Customer;
import com.bank.app.domain.repository.CustomerRepository;
import com.bank.app.domain.service.CustomerService;
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
            Customer customer = new Customer();
            customer.setName(dto.getName());
            customer.setGender(dto.getGender());
            customer.setIdentification(dto.getIdentification());
            customer.setAddress(dto.getAddress());
            customer.setPhone(dto.getPhone());
            customer.setPassword(dto.getPassword());
            customer.setStatus(dto.getStatus());
            customerRepository.save(customer);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<CustomerDTO> findAll() {
        return Flux.defer(() -> Flux.fromIterable(customerRepository.findAll()))
                .map(c -> new CustomerDTO(c.getId(), c.getName(), c.getGender(), c.getIdentification(), c.getAddress(), c.getPhone(), null, c.getStatus()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<CustomerDTO> findById(Long id) {
        return Mono.fromCallable(() -> customerRepository.findById(id))
                .flatMap(opt -> opt.map(c -> Mono.just(new CustomerDTO(c.getId(), c.getName(), c.getGender(), c.getIdentification(), c.getAddress(), c.getPhone(), null, c.getStatus())))
                        .orElse(Mono.empty()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<CustomerDTO> update(Long id, CustomerDTO dto) {
        return Mono.fromCallable(() -> {
            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            customer.setName(dto.getName());
            customer.setAddress(dto.getAddress());
            customer.setPhone(dto.getPhone());
            customer.setStatus(dto.getStatus());
            customerRepository.save(customer);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromRunnable(() -> customerRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic()).then();
    }
}