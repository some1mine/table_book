package site.jaeu95.table_book.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Store store;
    @OneToOne
    private Kiosk kiosk;
    @OneToOne
    private Customer customer;
    private LocalDateTime reservedTime;
}
