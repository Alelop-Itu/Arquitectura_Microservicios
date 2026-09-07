package com.bank.app.infrastructure.persistence.adapter;

import com.bank.app.infrastructure.mapper.MovementMapper;
import com.bank.app.domain.model.Movement;
import com.bank.app.domain.repository.MovementRepository;
import com.bank.app.infrastructure.persistence.repository.SpringDataMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepository {

    private final SpringDataMovementRepository springDataMovementRepository;

    @Override
    @Transactional
    public Movement save(Movement movement) {
        var entity = MovementMapper.toEntity(movement);
        var saved = springDataMovementRepository.save(entity);
        return MovementMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Movement> findById(Long id) {
        return springDataMovementRepository.findById(id)
                .map(MovementMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findAll() {
        return springDataMovementRepository.findAll().stream()
                .map(MovementMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        springDataMovementRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Movement> findByAccount_NumberAndDateBetween(String number, LocalDateTime start, LocalDateTime end) {
        return springDataMovementRepository.findByAccount_NumberAndDateBetween(number, start, end).stream()
                .map(MovementMapper::toDomain)
                .toList();
    }
}