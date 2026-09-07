package com.bank.app.domain.repository;

import com.bank.app.domain.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);
    Optional<Account> findById(Long id);
    Optional<Account> findByNumber(String number);
    List<Account> findAll();
    void deleteById(Long id);
    void deleteByNumber(String number);
}