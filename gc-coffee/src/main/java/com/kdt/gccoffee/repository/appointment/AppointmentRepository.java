package com.kdt.gccoffee.repository.appointment;

import com.kdt.gccoffee.model.Appointment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {
    Appointment insert(Appointment appointment);

    void deleteByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByCustomerId(UUID customerId);

    Optional<Appointment> findByDesignerId(UUID designerId);

    List<Appointment> findAll();

    Appointment updateByAppointmentId(Appointment appointment);
}
