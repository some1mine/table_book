package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import site.jaeu95.table_book.domain.form.AddStoreForm;

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
    @ColumnDefault(value = "0")
    private Double rating;
    private Double lat;
    private Double lnt;
    @ManyToOne
    @JsonBackReference
    private Manager manager;
    @OneToMany(mappedBy = "store")
    @JsonManagedReference
    private List<Review> reviews;
    @OneToMany(mappedBy = "store")
    @JsonManagedReference
    private List<Table> tables;

    public static Store of(Manager manager, AddStoreForm form) {
        return Store.builder()
                .lat(form.getLat())
                .lnt(form.getLnt())
                .name(form.getName())
                .manager(manager)
                .build();
    }
}
