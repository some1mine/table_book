package site.jaeu95.table_book.domain.form;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VisitTableForm {
    private Long reservationId;
    private Long storeId;
    private Long tableId;
    private String phone;
    private LocalDateTime reservedTime;

}
