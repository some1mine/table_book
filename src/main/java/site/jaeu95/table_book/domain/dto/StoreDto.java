package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Manager;
import site.jaeu95.table_book.domain.model.Review;
import site.jaeu95.table_book.domain.model.Store;
import site.jaeu95.table_book.domain.model.Table;

import java.util.List;

/**
 * 순환참조의 에러와 함께 점포 객체의 응답을 받아주기 위한 Dto 입니다.
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {
    private Long id;
    private String name;
    private Double rating;
    private Double lat;
    private Double lnt;
    private Manager manager;
    private List<Review> reviews;
    private List<Table> tables;

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .rating(store.getRating())
                .lat(store.getLat())
                .lnt(store.getLnt())
                .manager(store.getManager())
                .reviews(store.getReviews())
                .tables(store.getTables())
                .build();
    }
}
