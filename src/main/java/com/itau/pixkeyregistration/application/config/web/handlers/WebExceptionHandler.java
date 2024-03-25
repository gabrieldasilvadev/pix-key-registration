package com.itau.pixkeyregistration.application.config.web.handlers;

import com.itau.pixkeyregistration.application.web.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> onDefaultHandler(Exception ex, HttpServletRequest request) {
        log.error("Exception handled", ex);
        var details = new HashMap<String, String>();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDto(
                        HttpStatus.BAD_REQUEST.name(),
                        ex.getCause().getMessage(),
                        request.getRequestURI(),
                        details.put("cause", ex.getMessage())
                ));
    }


    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponseDto> onMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                              HttpServletRequest request) {
        log.error("MethodArgumentNotValidException handled", ex);
        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return responseInvalidArgs(details, request);
    }

    private ResponseEntity<ErrorResponseDto> responseInvalidArgs(Map<String, String> details, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ErrorResponseDto("ARGUMENT_NOT_VALID",
                        "Invalid arguments, see more in [details]",
                        request.getRequestURI(),
                        details
                )
        );
    }

    private ResponseEntity<ErrorResponseDto> responseUriNotFound(Map<String, String> details, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto("ENDPOINT_NOT_FOUND",
                        "The endpoint given was not found, see more in [details]",
                        request.getRequestURI(),
                        details
                )
        );
    }
}