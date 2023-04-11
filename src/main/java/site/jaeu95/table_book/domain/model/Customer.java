package site.jaeu95.table_book.domain.model;

import lombok.*;
import site.jaeu95.table_book.domain.form.SignUpForm;

import javax.persistence.*;

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

    public static Customer from(SignUpForm form) {
        return Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .phone(form.getPhone())
                .password(form.getPassword())
                .build();
    }
}
