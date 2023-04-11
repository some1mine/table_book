package site.jaeu95.table_book.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    USING_PHONE(HttpStatus.BAD_REQUEST, "이미 사용중인 번호입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "점장님 정보가 없어요");
    private final HttpStatus httpStatus;
    private final String detail;
}
