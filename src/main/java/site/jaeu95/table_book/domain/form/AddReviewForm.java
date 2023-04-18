package site.jaeu95.table_book.domain.form;

import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewForm {
    private Long customerId;
    private Long storeId;
    private Long reservationId;
    private String title;
    private String content;
    private Double rating;
}
