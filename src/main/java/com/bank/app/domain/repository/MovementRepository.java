package com.bank.app.domain.repository;

import com.bank.app.domain.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByAccount_NumberAndDateBetween(String accountNumber, LocalDateTime start, LocalDateTime end);

}
