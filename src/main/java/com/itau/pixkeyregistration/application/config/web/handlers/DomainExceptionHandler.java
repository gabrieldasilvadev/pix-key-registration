package com.itau.pixkeyregistration.application.config.web.handlers;

import com.itau.pixkeyregistration.application.web.dto.ErrorResponseDto;
import com.itau.pixkeyregistration.domain.exceptions.AccountAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.AccountLimitExceededException;
import com.itau.pixkeyregistration.domain.exceptions.AccountNotFoundException;
import com.itau.pixkeyregistration.domain.exceptions.AccountNumberExceededLimit;
import com.itau.pixkeyregistration.domain.exceptions.DomainException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountStatusException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAccountTypeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidAgencyException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidCellphoneException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidCellphonePixKeyException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidEmailException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPasswordException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonNameException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonTypeException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidPersonalDocumentException;
import com.itau.pixkeyregistration.domain.exceptions.InvalidRandomPixKeyException;
import com.itau.pixkeyregistration.domain.exceptions.PersonAlreadyExistsException;
import com.itau.pixkeyregistration.domain.exceptions.PersonNotFoundException;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyAlreadyInactivatedException;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyAlreadyRegisteredException;
import com.itau.pixkeyregistration.domain.exceptions.PixKeyNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DomainExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DomainExceptionHandler.class);
    private static final Map<String, HttpStatus> statusTable = new HashMap<>();

    static {
        statusTable.put("DomainException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponseDto> handleDomainException(DomainException ex, HttpServletRequest request) {
        logger.error("Exception handled: {}", ex.getMessage(), ex);

        HttpStatus status = mapStatus(ex);
        ErrorResponseDto errorResponse = new ErrorResponseDto(
                ex.getType(),
                ex.getMessage(),
                request.getRequestURI(),
                ex.getDetails()
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus mapStatus(DomainException ex) {
        return statusTable.getOrDefault(ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    static {
        // HTTP STATUS 422
        statusTable.put(InvalidAccountStatusException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidAccountTypeException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidAgeException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidAgencyException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidCellphoneException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidCellphonePixKeyException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidEmailException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidPasswordException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidPersonalDocumentException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidPersonNameException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidPersonTypeException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(InvalidRandomPixKeyException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(AccountLimitExceededException.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);
        statusTable.put(AccountNumberExceededLimit.class.getSimpleName(), HttpStatus.UNPROCESSABLE_ENTITY);

        // HTTP STATUS 409
        statusTable.put(AccountAlreadyExistsException.class.getSimpleName(), HttpStatus.CONFLICT);
        statusTable.put(PersonAlreadyExistsException.class.getSimpleName(), HttpStatus.CONFLICT);
        statusTable.put(PixKeyAlreadyInactivatedException.class.getSimpleName(), HttpStatus.CONFLICT);
        statusTable.put(PixKeyAlreadyRegisteredException.class.getSimpleName(), HttpStatus.CONFLICT);

        // HTTO STATUS 404
        statusTable.put(PixKeyNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND);
        statusTable.put(PersonNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND);
        statusTable.put(AccountNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND);
    }
}


