package com.bank.app.application.service;

import com.bank.app.application.dto.AccountDTO;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.Customer;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.domain.repository.CustomerRepository;
import com.bank.app.domain.service.AccountService;
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
            Account account = new Account();
            account.setNumber(dto.getNumber());
            account.setType(dto.getType());
            account.setBalance(dto.getBalance());
            account.setStatus(dto.getStatus());

            Customer customer = customerRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Error: El cliente con ID " + dto.getCustomerId() + " no existe."));
            account.setCustomer(customer);   customerRepository.findById(dto.getCustomerId())
                    .ifPresent(account::setCustomer);

            accountRepository.save(account);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<AccountDTO> findAll() {
        return Flux.defer(() -> Flux.fromIterable(accountRepository.findAll()))
                .map(a -> new AccountDTO(a.getNumber(), a.getType(), a.getBalance(), a.getStatus(),
                        a.getCustomer() != null ? a.getCustomer().getId() : null))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<AccountDTO> findByNumber(String number) {
        return Mono.fromCallable(() -> accountRepository.findByNumber(number))
                .flatMap(opt -> opt.map(a -> Mono.just(new AccountDTO(a.getNumber(), a.getType(), a.getBalance(), a.getStatus(),
                                a.getCustomer() != null ? a.getCustomer().getId() : null)))
                        .orElse(Mono.empty()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<AccountDTO> update(String number, AccountDTO dto) {
        return Mono.fromCallable(() -> {
            Account account = accountRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            account.setType(dto.getType());
            account.setStatus(dto.getStatus());
            account.setBalance(dto.getBalance());
            accountRepository.save(account);
            return dto;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(String number) {
        return Mono.fromRunnable(() -> {
            Account account = accountRepository.findByNumber(number)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            accountRepository.delete(account);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}