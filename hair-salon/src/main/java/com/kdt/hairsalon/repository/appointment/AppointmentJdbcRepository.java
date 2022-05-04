package com.kdt.hairsalon.repository.appointment;

import com.kdt.hairsalon.model.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.kdt.hairsalon.repository.JdbcUtils.toLocalDateTime;
import static com.kdt.hairsalon.repository.JdbcUtils.toUUID;

@Repository
@RequiredArgsConstructor
public class AppointmentJdbcRepository implements AppointmentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Appointment insert(Appointment appointment) {
        int update = jdbcTemplate.update("INSERT INTO appointments(appointment_id, menu_id, customer_id, designer_id, appointed_at) " +
                        "VALUES(UNHEX(REPLACE(:appointmentId, '-', ''))," +
                        " UNHEX(REPLACE(:menuId, '-', ''))," +
                        " UNHEX(REPLACE(:customerId, '-', ''))," +
                        " UNHEX(REPLACE(:designerId, '-', ''))," +
                        " :appointedAt)",
                toParamMap(appointment));

        if (update != 1) {
            throw new RuntimeException("데이터 저장에 실패 했습니다.");
        }

        return appointment;
    }

    @Override
    public void deleteByAppointmentId(UUID appointmentId) {
        int update = jdbcTemplate.update("DELETE FROM appointments WHERE appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))"
                , Collections.singletonMap("appointmentId", appointmentId.toString().getBytes()));

        if (update != 1) {
            throw new RuntimeException("데이터 삭제에 실패했습니다.");
        }
    }

    @Override
    public Optional<Appointment> findByAppointmentId(UUID appointmentId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM appointments WHERE appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))",
                    Collections.singletonMap("appointmentId", appointmentId.toString().getBytes()), appointmentRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<Appointment> findByCustomerId(UUID customerId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM appointments WHERE customer_id = UNHEX(REPLACE(:customerId, '-', ''))",
                            Collections.singletonMap("customerId", customerId.toString().getBytes()), appointmentRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public List<Appointment> findByDesignerId(UUID designerId) {
        return jdbcTemplate.query("SELECT * FROM appointments WHERE designer_id = UNHEX(REPLACE(:designerId, '-', ''))",
                Collections.singletonMap("designerId", designerId.toString().getBytes()), appointmentRowMapper);
    }

    @Override
    public List<AppointmentWithNames> findAll() {
        return jdbcTemplate.query("SELECT ap.appointment_id, c.name, d.name, m.name, ap.appointed_at FROM appointments AS ap " +
                "join customers AS c on ap.customer_id = c.id " +
                "join designers AS d on ap.designer_id = d.id " +
                "join menus AS m on ap.menu_id = m.id", appointmentJoinRowMapper);
    }

    @Override
    public Appointment updateByAppointmentId(Appointment appointment) {
        int update = jdbcTemplate.update("UPDATE appointments SET menu_id = UNHEX(REPLACE(:menuId, '-', '')), appointed_at = :appointedAt WHERE appointment_id = UNHEX(REPLACE(:appointmentId, '-', ''))",
                toParamMap(appointment));

        if (update != 1)
            throw new RuntimeException("예약 정보 수정에 실패했습니다.");

        return appointment;
    }

    private Map<String, Object> toParamMap(Appointment appointment) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("appointmentId", appointment.getAppointmentId().toString().getBytes());
        paramMap.put("customerId", appointment.getCustomerId().toString().getBytes());
        paramMap.put("menuId", appointment.getMenuId().toString().getBytes());
        paramMap.put("designerId", appointment.getDesignerId().toString().getBytes());
        paramMap.put("appointedAt", appointment.getAppointedAt());

        return paramMap;
    }

    private static final RowMapper<Appointment> appointmentRowMapper = (resultSet, i) -> {
        UUID appointmentId = toUUID(resultSet.getBytes("appointment_id"));
        UUID menuId = toUUID(resultSet.getBytes("menu_id"));
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        UUID designerId = toUUID(resultSet.getBytes("designer_id"));
        LocalDateTime appointedAt = toLocalDateTime(resultSet.getTimestamp("appointed_at"));

        return new Appointment(appointmentId, menuId, customerId, designerId, appointedAt);
    };

    private static final RowMapper<AppointmentWithNames> appointmentJoinRowMapper = (resultSet, i) -> {
        UUID appointmentId = toUUID(resultSet.getBytes("ap.appointment_id"));
        String menuName = resultSet.getString("m.name");
        String customerName = resultSet.getString("c.name");
        String designerName = resultSet.getString("d.name");
        LocalDateTime appointedAt = toLocalDateTime(resultSet.getTimestamp("ap.appointed_at"));

        return new AppointmentWithNames(appointmentId, designerName, menuName, customerName, appointedAt);
    };
}
