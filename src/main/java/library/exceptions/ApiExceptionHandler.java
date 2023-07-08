package library.exceptions;

import library.dtos.ErrorDto;
import library.dtos.ValidationErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorDto> handle(ApiException ex) {
        ErrorDto response = new ErrorDto();
        response.setErrorMessage(ex.getErrorMessage());
        response.setHttpStatusCode(ex.getHttpStatus().toString());
        response.setValidations(new ArrayList<>());
        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorDto> handle(BindException e) {
        List<ValidationErrorDto> validations = new ArrayList<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
            validations.add(new ValidationErrorDto(error.getField(), mensagem));
        }

        ErrorsTable tabela = ErrorsTable.ELEMENT_NOT_FOUND;
        ErrorDto erroResponseDto = new ErrorDto();
        erroResponseDto.setHttpStatusCode(tabela.getHttpStatus().toString());
        erroResponseDto.setErrorMessage(tabela.getErrorMessage());

        erroResponseDto.setValidations(validations);

        return ResponseEntity.status(tabela.getHttpStatus()).body(erroResponseDto);
    }
}
