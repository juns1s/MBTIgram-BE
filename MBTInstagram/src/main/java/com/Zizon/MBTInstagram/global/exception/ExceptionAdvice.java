package com.Zizon.MBTInstagram.global.exception;

import com.Zizon.MBTInstagram.responseDto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleBaseEx(CustomException exception) {
        log.error("CustomException errorMessage(): {}", exception.getMessage());
        log.error("CustomException HttpStatus(): {}", exception.getHttpStatus());

        return new ResponseEntity<>(new ExceptionDto(exception.getHttpStatus(), exception.getMessage()), HttpStatus.resolve(exception.getHttpStatus()));
    }
}


