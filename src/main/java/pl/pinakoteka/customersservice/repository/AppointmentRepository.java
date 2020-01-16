package pl.pinakoteka.customersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pinakoteka.customersservice.entity.Appointment;

import java.sql.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository <Appointment, Long> {
    List<Appointment> findAllByAppointmentDate(Date date);
}
