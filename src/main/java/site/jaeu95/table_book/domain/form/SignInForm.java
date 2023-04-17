package site.jaeu95.table_book.domain.form;

import lombok.Getter;

/**
 * 로그인 처리를 위한 Form 클래스입니다.
 */
@Getter
public class SignInForm {
    private String phone;
    private String password;
}
