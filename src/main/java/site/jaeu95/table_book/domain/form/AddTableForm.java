package site.jaeu95.table_book.domain.form;

import lombok.*;

/**
 * 테이블을 추가하기 위한 Form 클래스입니다.
 */
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AddTableForm {
    private Long storeId;
    private Integer size;
}
