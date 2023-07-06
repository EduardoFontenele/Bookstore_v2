package library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<Map<String, Object>> handleException(BindException exception) {

        List<String> errorsList = new ArrayList<>();

        exception.getFieldErrors().forEach(error -> errorsList.add(error.getField() + ": " + error.getDefaultMessage()));
        exception.getGlobalErrors().forEach(error -> errorsList.add(error.getObjectName() + ": " + error.getDefaultMessage()));

        Map<String, Object> errorResponse = Map.of(
                "error", errorsList,
                "message", exception.getLocalizedMessage(),
                "status", HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
