package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Review;
import site.jaeu95.table_book.domain.model.Store;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private Double rating;
    private Customer customer;
    private Store store;

    public static ReviewDto from(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .customer(review.getCustomer())
                .store(review.getStore())
                .build();
    }
}
