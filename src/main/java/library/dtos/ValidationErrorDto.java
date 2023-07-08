package library.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationErrorDto {
    private final String field;
    private final String message;
}
