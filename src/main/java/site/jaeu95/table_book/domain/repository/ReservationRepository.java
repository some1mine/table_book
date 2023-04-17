package site.jaeu95.table_book.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.jaeu95.table_book.domain.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByReservedTimeIsBeforeAndCheckOutedIsFalse(LocalDateTime now);

}
