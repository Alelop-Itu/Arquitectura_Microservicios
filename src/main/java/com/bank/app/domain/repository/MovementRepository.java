package com.bank.app.domain.repository;

import com.bank.app.domain.model.Movement;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovementRepository {
    Movement save(Movement movement);
    Optional<Movement> findById(Long id);
    List<Movement> findAll();
    void deleteById(Long id);
    List<Movement> findByAccount_NumberAndDateBetween(String number, LocalDateTime start, LocalDateTime end);
}