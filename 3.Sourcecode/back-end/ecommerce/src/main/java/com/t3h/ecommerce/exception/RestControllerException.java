package com.t3h.ecommerce.exception;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter


public class RestControllerException extends BaseException {
    public RestControllerException(int httpStatus, int errorCode, String errorMsg, String nameFunction, String path) {
        super(httpStatus, errorCode, nameFunction, errorMsg, path);
    }

    public RestControllerException(int httpStatus, int errorCode, String nameFunction, String errorMessage, String path, LocalDateTime errorDate) {
        super(httpStatus, errorCode, nameFunction, errorMessage, path, errorDate);
    }

}
