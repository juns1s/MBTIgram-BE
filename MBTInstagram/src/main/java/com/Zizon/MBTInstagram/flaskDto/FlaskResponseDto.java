package com.Zizon.MBTInstagram.flaskDto;

import lombok.Data;

import java.util.Map;

@Data
public class FlaskResponseDto {

    private String mbti;
    private Map<String, Double> prob;
}
