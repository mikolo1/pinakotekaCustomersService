package pl.pinakoteka.customersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pinakoteka.customersservice.entity.Role;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByRole(String role);
}

