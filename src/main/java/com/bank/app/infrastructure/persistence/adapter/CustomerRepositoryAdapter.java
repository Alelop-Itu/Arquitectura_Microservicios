package com.bank.app.infrastructure.persistence.adapter;

import com.bank.app.infrastructure.mapper.CustomerMapper;
import com.bank.app.domain.model.Customer;
import com.bank.app.domain.repository.CustomerRepository;
import com.bank.app.infrastructure.persistence.repository.SpringDataCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryAdapter implements CustomerRepository {

    private final SpringDataCustomerRepository springDataCustomerRepository;

    @Override
    @Transactional
    public Customer save(Customer customer) {
        var entity = CustomerMapper.toEntity(customer);
        var saved = springDataCustomerRepository.save(entity);
        return CustomerMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return springDataCustomerRepository.findById(id)
                .map(CustomerMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return springDataCustomerRepository.findAll().stream()
                .map(CustomerMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        springDataCustomerRepository.deleteById(id);
    }
}