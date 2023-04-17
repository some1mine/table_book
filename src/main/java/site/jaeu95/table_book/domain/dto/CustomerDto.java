package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Customer;

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
    private String phone;

    public static CustomerDto from(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .build();
    }
}
