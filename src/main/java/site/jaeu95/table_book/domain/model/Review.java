package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Double rating;
    @ManyToOne
    @JsonBackReference
    private Store store;
    @ManyToOne
    @JsonBackReference
    private Customer customer;
}
