package kg.peaksoft.bilingualb4.exception;

import kg.peaksoft.bilingualb4.exception.exceptionResponse.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(BadRequestException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST);
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleAnyExceptions(NotFoundException e) {
        e.printStackTrace();
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.NOT_FOUND);
        exceptionResponse.setMessage(e.getMessage());
        return exceptionResponse;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handleBadCredentialsException(BadCredentialsException badCredentialsException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setStatus(HttpStatus.UNAUTHORIZED);
        exceptionResponse.setMessage(badCredentialsException.getMessage());
        return exceptionResponse;
    }
}
