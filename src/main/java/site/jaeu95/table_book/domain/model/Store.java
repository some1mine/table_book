package site.jaeu95.table_book.domain.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double rating;
    private Double lat;
    private Double lnt;
    @ManyToOne
    private Manager manager;
    @OneToMany
    private List<Review> reviews;
    @OneToMany
    private List<Table> tables;
}
