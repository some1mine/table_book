package site.jaeu95.table_book.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import site.jaeu95.table_book.domain.form.AddReviewForm;

import javax.persistence.*;

/**
 * 리뷰의 Entity 를 만들어 두었습니다.
 */

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

    public static Review from(Customer customer, Store store, AddReviewForm form) {
        return Review.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .rating(form.getRating())
                .store(store)
                .customer(customer)
                .build();
    }
}
