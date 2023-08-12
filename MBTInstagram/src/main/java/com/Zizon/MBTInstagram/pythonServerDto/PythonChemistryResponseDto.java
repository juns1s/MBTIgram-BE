package com.Zizon.MBTInstagram.pythonServerDto;

import lombok.Data;

import java.util.List;

@Data
public class PythonChemistryResponseDto {
    private int member;
    private List<String> member_mbti;

    private double avg;

    private List<MemberData> member_data;

    @Data
    private static class MemberData{
        private String instaId;
        private String mbti;
        private List<Double> relationships;
    }
}
