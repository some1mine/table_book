package site.jaeu95.table_book.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
