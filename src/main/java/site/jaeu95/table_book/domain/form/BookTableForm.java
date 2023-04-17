package site.jaeu95.table_book.domain.form;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 테이블을 예약하고 예약정보를 추가하기 위한 위한 Form 클래스입니다.
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BookTableForm {
    private Long storeId;
    private Long tableId;
    private Integer size;
    private LocalDateTime reservedAt;
}
