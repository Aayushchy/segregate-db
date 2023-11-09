package com.esewa.configuration;

import com.esewa.constants.ResponseMessage;
import com.esewa.constants.ResponseStatusCode;
import com.esewa.dto.ExceptionDto;
import com.esewa.exception.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> exception(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ExceptionDto(ResponseStatusCode.INTERNAL_SERVER_ERROR,ResponseMessage.REQUEST_FAILED), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<ExceptionDto> fileException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        return new ResponseEntity<>(new ExceptionDto(ResponseStatusCode.BAD_REQUEST,ResponseMessage.REQUEST_FAILED), HttpStatus.BAD_REQUEST );
    }

}
