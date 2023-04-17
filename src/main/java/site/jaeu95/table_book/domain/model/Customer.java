package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(nullable = false, unique = true)
    private String phone;
    private String password;
    @OneToMany(mappedBy = "customer")
    @JsonManagedReference
    private List<Reservation> reservations;
    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<Review> reviews;

    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .password(form.getPassword())
                .build();
    }
}
