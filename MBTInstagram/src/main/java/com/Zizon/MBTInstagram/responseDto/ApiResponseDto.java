package com.Zizon.MBTInstagram.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponseDto {
    private int status;
    private boolean success;
    private String message;

    public ApiResponseDto(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}
