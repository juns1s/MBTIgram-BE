package com.Zizon.MBTInstagram.global.exception;

import com.Zizon.MBTInstagram.responseDto.ApiResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLTransientConnectionException;
import java.sql.SQLTransientException;

@RestControllerAdvice
@Slf4j
public class SqlExceptionHandler {
    @ExceptionHandler(SQLTransientConnectionException.class)
    public ResponseEntity<ApiResponseDto> handleSQLException(SQLTransientException e){
        return new ResponseEntity<>(new ApiResponseDto(500, false, "서버 내 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<ApiResponseDto> handleTransactionException(CannotCreateTransactionException e){
        return new ResponseEntity<>(new ApiResponseDto(500, false, "서버 내 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
