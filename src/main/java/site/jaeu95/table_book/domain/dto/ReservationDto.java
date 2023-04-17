package site.jaeu95.table_book.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import site.jaeu95.table_book.domain.model.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long id;
    private Store store;
    private Table table;
    private Customer customer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime reservedTime;

    public static ReservationDto from(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .store(reservation.getStore())
                .table(reservation.getTable())
                .customer(reservation.getCustomer())
                .reservedTime(reservation.getReservedTime())
                .build();
    }
}
