package com.bank.app.application.service.impl;

import com.bank.app.application.dto.MovementDTO;
import com.bank.app.application.service.MovementService;
import com.bank.app.common.exception.MovementNotFoundException;
import com.bank.app.domain.repository.MovementRepository;
import com.bank.app.infrastructure.mapper.MovementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final MovementTransactionalExecutor movementTransactionalExecutor;

    @Override
    public Mono<MovementDTO> createMovement(MovementDTO dto) {
        return Mono.fromCallable(() -> movementTransactionalExecutor.createMovement(dto))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Flux<MovementDTO> getAllMovements() {
        return Mono.fromCallable(movementRepository::findAll)
                .flatMapMany(Flux::fromIterable)
                .map(MovementMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MovementDTO> findById(Long id) {
        return Mono.fromCallable(() -> movementRepository.findById(id)
                        .orElseThrow(() -> new MovementNotFoundException(id)))
                .map(MovementMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<MovementDTO> update(Long id, MovementDTO dto) {
        return Mono.fromCallable(() -> movementTransactionalExecutor.updateMovement(id, dto))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<Void> delete(Long id) {
        return Mono.fromCallable(() -> movementRepository.findById(id)
                        .orElseThrow(() -> new MovementNotFoundException(id)))
                .doOnNext(movement -> movementRepository.deleteById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }

    @Override
    public Flux<MovementDTO> getMovementsByAccountAndDates(String accountNumber, LocalDateTime start, LocalDateTime end) {
        return Mono.fromCallable(() -> movementRepository.findByAccount_NumberAndDateBetween(accountNumber, start, end))
                .flatMapMany(Flux::fromIterable)
                .map(MovementMapper::toDto)
                .subscribeOn(Schedulers.boundedElastic());
    }
}