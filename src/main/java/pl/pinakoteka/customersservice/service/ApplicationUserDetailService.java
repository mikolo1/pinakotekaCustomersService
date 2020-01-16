package pl.pinakoteka.customersservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pinakoteka.customersservice.entity.Role;
import pl.pinakoteka.customersservice.entity.User;
import pl.pinakoteka.customersservice.repository.UserRepository;



@Service
@RequiredArgsConstructor
public class ApplicationUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User entity = userRepository.findUserByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User " + phoneNumber + " not found"));
        String[] roles = entity.getRoles().stream().map(Role::getRole).toArray(String[]::new);
        return org.springframework.security.core.userdetails.User
                .withUsername(entity.getPhoneNumber()).password(entity.getPassword()).roles(roles)
                .build();
    }
}