package site.jaeu95.table_book.domain.form;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 매장 방문 처리를 위한 Form 클래스입니다.
 */
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
