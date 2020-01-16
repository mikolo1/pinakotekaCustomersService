package pl.pinakoteka.customersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pinakoteka.customersservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByUsername(String username);
    Optional<User> findUserByPhoneNumber(String phoneNumber);
    User findByPhoneNumber (String phoneNumber);

//    @Query("SELECT u from User u where u.phoneNumber = :phone")
//    Optional<User>findByPhone(@Param("phone") String phone);

}

