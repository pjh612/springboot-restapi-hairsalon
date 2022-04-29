package com.kdt.gccoffee.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Customer {
    private final UUID id;
    private final String name;
    private String email;
    private final Gender gender;
    private final LocalDate birth;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public Customer(UUID id, String name, String email, Gender gender, LocalDate birth, LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
