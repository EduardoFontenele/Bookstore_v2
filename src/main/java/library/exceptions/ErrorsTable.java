package library.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorsTable {
    ELEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Element with id [replace] not found");

    private final HttpStatus httpStatus;
    private final String errorMessage;

    ErrorsTable(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
