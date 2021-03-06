package com.kdt.hairsalon.service.customer;

import com.kdt.hairsalon.model.Customer;
import com.kdt.hairsalon.model.Gender;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class CustomerDto {
    private final UUID id;
    private final String name;
    private final String email;
    private final Gender gender;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String comment;

    public CustomerDto(UUID id, String name, String email, Gender gender, LocalDate birth,
                       LocalDateTime createdAt, LocalDateTime updatedAt, String comment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comment = comment;
    }

    public static CustomerDto of(Customer customer) {
        return new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getGender(),
                customer.getBirth(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getComment());
    }
}
