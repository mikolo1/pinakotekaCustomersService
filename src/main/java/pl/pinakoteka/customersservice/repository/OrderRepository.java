package pl.pinakoteka.customersservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.pinakoteka.customersservice.entity.Status;
import pl.pinakoteka.customersservice.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPhoneNumber(String phoneNumber);

    List<Order> findAllByStatus(Status status);

    @Override
    Page<Order> findAll(Pageable pageable);

    Page<Order> findAllByPhoneNumber(Pageable pageable, String phoneNumber);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT o FROM Order o WHERE " +
            "o.orderNo LIKE CONCAT('%',:searchText,'%') " +
            "OR o.comments LIKE CONCAT('%',:searchText,'%') " +
            "OR o.dateOfOrder LIKE CONCAT('%',:searchText,'%') " +
            "OR o.estimatedDate LIKE CONCAT('%',:searchText,'%')" +
            "OR o.status LIKE CONCAT('%',:searchText,'%')" +
            "OR o.value LIKE CONCAT('%',:searchText,'%')" +
            "OR o.phoneNumber LIKE CONCAT('%',:searchText,'%')")
    Page<Order> findAllBySearchText(Pageable pageable, String searchText);

    @Query("select avg(o.value) from Order o where o.dateOfOrder > :date")
    Double findMiddleOrderValueFromLastYear(LocalDate date);

    @Query("select count (o) from Order o where o.dateOfOrder > :date")
    Integer findNumberOfOrdersFromLastYear(LocalDate date);

}
