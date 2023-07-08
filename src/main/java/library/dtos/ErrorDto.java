package library.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorDto {
    private String httpStatusCode;
    private String errorMessage;
    private List<ValidationErrorDto> validations;
}
