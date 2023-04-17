package site.jaeu95.table_book.exception;

import lombok.Getter;

/**
 * 커스텀 에러 클래스입니다.
 */
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getDetail());
        this.errorCode = errorCode;
    }
}
