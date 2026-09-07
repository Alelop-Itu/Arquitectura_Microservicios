package com.bank.app.infrastructure.persistence.repository;

import com.bank.app.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataAccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByNumber(String number);
}