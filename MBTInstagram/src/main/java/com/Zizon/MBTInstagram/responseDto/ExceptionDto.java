package com.Zizon.MBTInstagram.responseDto;

public class ExceptionDto extends ApiResponseDto {
    public ExceptionDto(int status, String message) {
        super(status, false, message);
    }
}