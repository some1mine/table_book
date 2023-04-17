package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Table;

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
