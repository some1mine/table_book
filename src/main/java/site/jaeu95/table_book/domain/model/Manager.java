package site.jaeu95.table_book.domain.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import site.jaeu95.table_book.domain.form.SignUpForm;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    private String password;
    @ColumnDefault(value = "true")
    private boolean isPartner;
    @OneToMany
    private List<Store> stores;

    public static Manager from(SignUpForm form) {
        return Manager.builder()
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .password(form.getPassword())
                .build();
    }
}
