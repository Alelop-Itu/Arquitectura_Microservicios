package com.bank.app.domain.service;

import com.bank.app.application.dto.AccountDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Mono<AccountDTO> save(AccountDTO dto);
    Flux<AccountDTO> findAll();
    Mono<AccountDTO> findByNumber(String number);
    Mono<AccountDTO> update(String number, AccountDTO dto);
    Mono<Void> delete(String number);
}
