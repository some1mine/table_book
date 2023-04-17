package site.jaeu95.table_book.domain.form;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AddTableForm {
    private Long storeId;
    private Integer size;
}
