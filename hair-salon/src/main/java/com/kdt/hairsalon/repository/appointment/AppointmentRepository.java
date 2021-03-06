package com.kdt.hairsalon.repository.appointment;

import com.kdt.hairsalon.model.Appointment;
import com.kdt.hairsalon.model.AppointmentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository {
    Appointment insert(Appointment appointment);

    void deleteByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByAppointmentId(UUID appointmentId);

    Optional<Appointment> findByCustomerId(UUID customerId);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findByDesignerId(UUID designerId);

    List<Appointment> findAll();

    Appointment updateByAppointmentId(Appointment appointment);
}
