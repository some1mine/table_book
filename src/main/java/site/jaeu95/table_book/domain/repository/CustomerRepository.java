package site.jaeu95.table_book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jaeu95.table_book.domain.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
     boolean existsByPhone(String phone);
}
