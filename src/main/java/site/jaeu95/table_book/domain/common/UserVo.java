package site.jaeu95.table_book.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 인증 처리를 위해 만들 Vo입니다.
 */
@AllArgsConstructor
@Getter
public class UserVo {
    private Long id;
    private String phone;
}
