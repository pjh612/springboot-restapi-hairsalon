package com.kdt.hairsalon.service.customer;

import com.kdt.hairsalon.model.Customer;
import com.kdt.hairsalon.model.Gender;
import com.kdt.hairsalon.repository.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerDto create(String name, String email, Gender gender, LocalDate birth) {
        Customer customer = new Customer(UUID.randomUUID(), name, email, gender, birth, LocalDateTime.now(), LocalDateTime.now(), "");

        customerRepository.insert(customer);

        return CustomerDto.of(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findById(UUID id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID로 Customer를 찾을 수 없습니다."));

        return CustomerDto.of(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findByName(String name) {
        return customerRepository.findByName(name)
                .stream()
                .map(CustomerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDto findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 email로 Customer를 찾을 수 없습니다."));

        return CustomerDto.of(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDto> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto updateComment(UUID id, String comment) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 ID로 Customer를 찾을 수 없습니다."));

        customer.setComment(comment);

        customerRepository.update(customer);

        return CustomerDto.of(customer);
    }
}
