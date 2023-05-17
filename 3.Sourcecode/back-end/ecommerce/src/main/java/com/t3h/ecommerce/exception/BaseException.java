package com.t3h.ecommerce.exception;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode()
@Getter
@Setter
@JsonIgnoreProperties(value = {"cause", "localizedMessage", "message", "nameFunction", "stackTrace", "suppressed"})
@AllArgsConstructor
@NoArgsConstructor

public class BaseException {
    private int status;
    private int code;
    private String nameFunction;
    private String error;
    private String path;
    @JsonFormat(pattern = "dd/MM/yyy HH:mm:ss")
    private LocalDateTime date;


    public BaseException(int status, int code, String nameFunction, String error, String path) {
        super();
        this.status = status;
        this.code = code;
        this.nameFunction = nameFunction;
        this.error = error == null ? getError() : error;
        this.path = path;
        this.date = LocalDateTime.now();
    }

}
