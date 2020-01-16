package pl.pinakoteka.customersservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import pl.pinakoteka.customersservice.entity.Role;
import pl.pinakoteka.customersservice.repository.RoleRepository;

@RequiredArgsConstructor
@Configuration
public class LaunchConfig {

    private final RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadRoles() {

        Role roleAdmin = roleRepository.findByRole("ADMIN");
        Role roleUser = roleRepository.findByRole("USER");

        if (roleAdmin == null) {
            roleAdmin = new Role(1L, "ADMIN");
            roleRepository.save(roleAdmin);
        }
        if (roleUser == null) {
            roleUser = new Role(2L, "USER");
            roleRepository.save(roleUser);
        }
    }
}
