package com.bank.app.infrastructure.persistence.repository;

import com.bank.app.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataMovementRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByAccount_NumberAndDateBetween(String number, LocalDateTime start, LocalDateTime end);
}
