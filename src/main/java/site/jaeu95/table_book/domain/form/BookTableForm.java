package site.jaeu95.table_book.domain.form;

import lombok.*;

import java.time.LocalDateTime;

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
