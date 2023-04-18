package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Customer;
import site.jaeu95.table_book.domain.model.Reservation;
import site.jaeu95.table_book.domain.model.Review;

import java.util.List;

/**
 * 순환참조의 에러와 함께 고객 객체의 응답을 받아주기 위한 Dto 입니다.
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<Reservation> reservations;
    private List<Review> reviews;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .reservations(customer.getReservations())
                .reviews(customer.getReviews())
                .build();
    }
}
