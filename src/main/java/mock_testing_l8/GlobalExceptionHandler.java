package mock_testing_l8;

import jakarta.servlet.http.HttpServletRequest;
import mock_testing_l8.dto.AppErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<AppErrorDto> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> developerMessage = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            developerMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        AppErrorDto appErrorDto = new AppErrorDto(request.getRequestURI(), 400, "Invalid Input", developerMessage);
        return new ResponseEntity<>(appErrorDto, HttpStatus.BAD_REQUEST);
    }
}
