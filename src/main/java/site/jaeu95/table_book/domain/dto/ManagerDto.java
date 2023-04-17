package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Manager;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 순환참조의 에러와 함께 점장 객체의 응답을 받아주기 위한 Dto 입니다.
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ManagerDto {
    private Long id;
    private String name;
    private String phone;
    private List<StoreDto> stores;

    public static ManagerDto from(Manager manager) {
        List<StoreDto> stores = manager.getStores().stream().map(StoreDto::from).collect(Collectors.toList());
        return ManagerDto.builder()
                .id(manager.getId())
                .name(manager.getName())
                .phone(manager.getPhone())
                .stores(stores)
                .build();
    }
}
