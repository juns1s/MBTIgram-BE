package com.Zizon.MBTInstagram.pythonServerDto;

import lombok.Data;

import java.util.Map;

@Data
public class PythonMbtiResponseDto {

    private String mbti;
    private Map<String, Double> prob;
}
