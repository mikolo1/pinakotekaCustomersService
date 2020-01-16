package pl.pinakoteka.customersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pinakoteka.customersservice.entity.Sms;

@Repository
public interface SmsRepository extends JpaRepository <Sms, Long> {
}
