package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import site.jaeu95.table_book.domain.form.AddTableForm;

import javax.persistence.*;


/**
 * 점포의 Entity 입니다. 순환참조 방지를 위해 JsonBackReference 를 사용했습니다.
 */

@Entity
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@javax.persistence.Table(name = "store_table")
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JsonBackReference
    private Store store;
    private Integer size;
    @ColumnDefault(value = "false")
    @EqualsAndHashCode.Exclude
    private Boolean booked;

    public static Table fromAddForm(AddTableForm form) {
        return Table.builder()
                .size(form.getSize())
                .booked(false)
                .build();
    }

}
