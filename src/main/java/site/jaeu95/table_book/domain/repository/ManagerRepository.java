package site.jaeu95.table_book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jaeu95.table_book.domain.model.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByPhone(String phone);
}
