package com.bank.app.application.service.impl;

import com.bank.app.application.dto.AccountDTO;
import com.bank.app.application.service.AccountService;
import com.bank.app.common.exception.AccountNotFoundException;
import com.bank.app.common.exception.CustomerNotFoundException;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.Customer;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.domain.repository.CustomerRepository;
import com.bank.app.infrastructure.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Mono<AccountDTO> save(AccountDTO dto) {
        return Mono.fromCallable(() -> {
            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new CustomerNotFoundException(dto.getCustomerId()));

            Account account = new Account();
            account.setNumber(dto.getNumber());
            account.setType(dto.getType());
            account.setBalance(dto.getBalance());
            account.setStatus(dto.getStatus());
            account.setCustomer(customer);

            Account saved = accountRepository.save(account);
            return AccountMapper.toDto(saved);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<AccountDTO> findAll() {
        return Mono.fromCallable(accountRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .map(AccountMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<AccountDTO> findByNumber(String number) {
        return Mono.fromCallable(() -> accountRepository.findByNumber(number)
                        .orElseThrow(() -> new AccountNotFoundException(number)))
                .map(AccountMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<AccountDTO> update(String number, AccountDTO dto) {
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findByNumber(number)
                    .orElseThrow(() -> new AccountNotFoundException(number));

            account.setType(dto.getType());
            account.setStatus(dto.getStatus());
            account.setBalance(dto.getBalance());

            Account updated = accountRepository.save(account);
            return AccountMapper.toDto(updated);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(String number) {
        return Mono.fromCallable(() -> accountRepository.findByNumber(number)
                        .orElseThrow(() -> new AccountNotFoundException(number)))
                .doOnNext(account -> accountRepository.deleteByNumber(number))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }
}