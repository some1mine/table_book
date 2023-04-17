package site.jaeu95.table_book.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 점포를 추가하기 위한 Form 클래스입니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStoreForm {
    private String name;
    private Double lat;
    private Double lnt;
    private List<AddTableForm> tables;
}
