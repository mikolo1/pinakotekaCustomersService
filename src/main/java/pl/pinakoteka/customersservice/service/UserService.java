package pl.pinakoteka.customersservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pinakoteka.customersservice.dto.UserDto;
import pl.pinakoteka.customersservice.entity.Role;
import pl.pinakoteka.customersservice.entity.User;
import pl.pinakoteka.customersservice.repository.RoleRepository;
import pl.pinakoteka.customersservice.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public void create(UserDto userDto) {
        User entity = null;
        Role role = roleRepository.findByRole("USER");

        Optional<User> user = userRepository.findUserByPhoneNumber(userDto.getPhoneNumber());
        if (user.isPresent()) {
            entity = user.get();
        }
        else {
            entity = new User();
            entity.setRoles(Collections.singleton(role));
            entity.setPhoneNumber(userDto.getPhoneNumber());
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        entity.setPassword(encodedPassword);
        entity.setEmail(userDto.getEmail());
        userRepository.save(entity);

    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber){
        return userRepository.findUserByPhoneNumber(phoneNumber);
    }

    public User findByPhoneNumber(String phoneNumber){
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User uptade(User user) {
        return userRepository.save(user);
    }
}