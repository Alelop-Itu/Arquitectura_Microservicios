package com.bank.app.infrastructure.persistence.adapter;

import com.bank.app.infrastructure.mapper.AccountMapper;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.repository.AccountRepository;
import com.bank.app.infrastructure.persistence.repository.SpringDataAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepository {

    private final SpringDataAccountRepository springDataAccountRepository;

    @Override
    @Transactional
    public Account save(Account account) {
        var entity = AccountMapper.toEntity(account);
        var saved = springDataAccountRepository.save(entity);
        return AccountMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(Long id) {
        return springDataAccountRepository.findById(id)
                .map(AccountMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findByNumber(String number) {
        return springDataAccountRepository.findByNumber(number)
                .map(AccountMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> findAll() {
        return springDataAccountRepository.findAll().stream()
                .map(AccountMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        springDataAccountRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByNumber(String number) {
        springDataAccountRepository.findByNumber(number)
                .ifPresent(springDataAccountRepository::delete);
    }
}