package site.jaeu95.table_book.domain.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입을 위한 Form 클래스입니다.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
    private String name;
    private String email;
    private String phone;
    private String password;
}
