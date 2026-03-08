package com.bank.app.domain.repository;

import com.bank.app.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


    @DataJpaTest
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
    class EntityMappingTest {

        @Autowired
        private CustomerRepository customerRepository;

        @Test
        void shouldSaveAndLoadCustomer() {
            Customer customer = new Customer();
            customer.setName("Alejandra Lopez");
            customer.setIdentification("1712345678");
            customer.setPassword("secure123");
            customer.setStatus(true);

            Customer saved = customerRepository.save(customer);


            assertNotNull(saved.getId());
            assertEquals("Alejandra Lopez", saved.getName());
        }
    }
