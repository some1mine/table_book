package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 예약정보 Entity 입니다. 순환참조 방지를 위해 JsonBackReference 를 사용했습니다.
 */
@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Store store;
    @OneToOne
    private Table table;
    @ManyToOne
    @JoinColumn(name = "kiosk_id")
    private Kiosk kiosk;
    @ManyToOne
    @JsonBackReference
    private Customer customer;
    private LocalDateTime reservedTime;
    @EqualsAndHashCode.Exclude
    @ColumnDefault(value = "false")
    private boolean expired;
    @EqualsAndHashCode.Exclude
    @ColumnDefault(value = "false")
    private boolean checkedOuted;
}
