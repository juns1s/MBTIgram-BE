package com.Zizon.MBTInstagram.responseDto;

import lombok.Data;

@Data
public class FlaskResponseDto {
    private String mbti;

    public FlaskResponseDto(String mbti) {
        this.mbti = mbti;
    }
}
