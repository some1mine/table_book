package site.jaeu95.table_book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jaeu95.table_book.domain.model.Table;

public interface TableRepository extends JpaRepository<Table, Long> {
}
