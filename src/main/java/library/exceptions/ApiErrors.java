package library.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrors {

    ELEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Element doesn't exist in database");


    private final HttpStatus httpStatus;
    private final String message;

    ApiErrors(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
