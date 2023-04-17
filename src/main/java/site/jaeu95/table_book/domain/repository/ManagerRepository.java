package site.jaeu95.table_book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jaeu95.table_book.domain.model.Manager;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByPhone(String phone);

    Optional<Manager> findByPhone(String phone);
}
