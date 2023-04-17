package site.jaeu95.table_book.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    USING_PHONE(HttpStatus.BAD_REQUEST, "이미 사용중인 번호입니다."),
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원 정보가 없어요"),
    NOT_FOUND_STORE(HttpStatus.BAD_REQUEST, "매장을 찾을 수 없어요"),
    NOT_FOUND_TABLE(HttpStatus.BAD_REQUEST, "테이블을 찾을 수 없어요"),
    NOT_USER_STORE(HttpStatus.BAD_REQUEST, "당신의 매장이 아닙니다."),
    NOT_USER_RESERVATION(HttpStatus.BAD_REQUEST, "예약 정보를 찾을 수 없습니다."),
    ALREADY_BOOKED_TABLE(HttpStatus.BAD_REQUEST, "이미 예약된 테이블입니다.");
    private final HttpStatus httpStatus;
    private final String detail;
}
