package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Table;

/**
 * 순환참조의 에러와 함께 테이블 객체의 응답을 받아주기 위한 Dto 입니다.
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    private Long id;
    private Integer size;
    private Boolean booked;

    public static TableDto from(Table table) {
        return TableDto.builder()
                .id(table.getId())
                .size(table.getSize())
                .booked(table.getBooked())
                .build();
    }
}
