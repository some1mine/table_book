package site.jaeu95.table_book.domain.dto;

import lombok.*;
import site.jaeu95.table_book.domain.model.Manager;

import java.util.List;
import java.util.stream.Collectors;

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
