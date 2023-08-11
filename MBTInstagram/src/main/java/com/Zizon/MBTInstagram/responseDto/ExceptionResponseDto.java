package com.Zizon.MBTInstagram.responseDto;

public class ExceptionResponseDto extends ApiResponseDto {
    public ExceptionResponseDto(int status, String message) {
        super(status, false, message);
    }
}