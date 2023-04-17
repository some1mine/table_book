package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Store;

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

    public static StoreDto from(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .rating(store.getRating())
                .build();
    }
}
