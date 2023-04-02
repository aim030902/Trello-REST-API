package uz.aim.trellorestapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.aim.trellorestapi.dtos.response.ApiErrorResponse;
import uz.aim.trellorestapi.dtos.response.ApiResponse;
import uz.aim.trellorestapi.exception.GenericConflictException;
import uz.aim.trellorestapi.exception.GenericInvalidTokenException;
import uz.aim.trellorestapi.exception.GenericNotFoundException;
import uz.aim.trellorestapi.exception.GenericRuntimeException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Abbosbek Murodov
 * @since : 02/04/23 / 11:16
 * Project : Trello-REST-API / IntelliJ IDEA
 */

@RestControllerAdvice
public class GenericGlobalExceptionHandler {
    @ExceptionHandler(GenericNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ApiErrorResponse> notFound(GenericNotFoundException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .requestPath(request.getRequestURL().toString())
                .build());
    }
    @ExceptionHandler(GenericConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiResponse<ApiErrorResponse> conflict(GenericConflictException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .requestPath(request.getRequestURL().toString())
                .build());
    }
    @ExceptionHandler(GenericInvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ApiErrorResponse> invalidToken(GenericInvalidTokenException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .requestPath(request.getRequestURL().toString())
                .build());
    }
    @ExceptionHandler(GenericRuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ApiErrorResponse> runtime(GenericRuntimeException e, HttpServletRequest request) {
        return new ApiResponse<>(ApiErrorResponse.builder()
                .friendlyMessage(e.getMessage())
                .requestPath(request.getRequestURL().toString())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, Object> errors = new HashMap<>();
        BindingResult bindingResult = ex.getBindingResult();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        String requestURI = request.getRequestURI();
        return new ResponseEntity<>(ApiErrorResponse
                .builder()
                .friendlyMessage("Invalid Params Provided")
                .errorFields(errors)
                .requestPath(requestURI)
                .build(), HttpStatus.BAD_REQUEST);
    }
}

