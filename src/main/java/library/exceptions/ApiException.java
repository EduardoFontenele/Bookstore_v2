package library.exceptions;

import library.dtos.ValidationErrorDto;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class ApiException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final List<ValidationErrorDto> validations;

    public ApiException(ErrorsTable table, String item, List<ValidationErrorDto> validations) {
        super();
        this.httpStatus = table.getHttpStatus();
        this.errorMessage = table.getErrorMessage().replace("[replace]", item);
        this.validations = validations;
    }

    public ApiException(ErrorsTable table, String item) {
        super();
        this.httpStatus = table.getHttpStatus();
        this.errorMessage = table.getErrorMessage().replace("[replace]", item);
        this.validations = new ArrayList<>();
    }

    public ApiException(ErrorsTable table) {
        super();
        this.httpStatus = table.getHttpStatus();
        this.errorMessage = table.getErrorMessage();
        this.validations = new ArrayList<>();
    }
}
