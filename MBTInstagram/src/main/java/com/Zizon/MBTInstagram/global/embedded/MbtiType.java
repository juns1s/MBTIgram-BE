package com.Zizon.MBTInstagram.global.embedded;

public enum MbtiType {

    INTJ("INTJ","인티제"),
    INTP("INTP", "인팁"),
    ENTJ("ENTJ", "인티제"),
    ENTP("ENTP", "인팁"),
    INFJ("INFJ", "인프제"),
    INFP("INFP", "인프피"),
    ENFJ("ENFJ", "엔프제"),
    ENFP("ENFP", "엔프피"),
    ISTJ("ISTJ", "잇티제"),
    ISFJ("ISFJ", "잇프제"),
    ESTJ("ESTJ", "엣티제"),
    ESFJ("ESFJ", "엣프제"),
    ISTP("ISTP", "잇팁"),
    ISFP("ISFP", "잇프피"),
    ESTP("ESTP", "엣팁"),
    ESFP("ESFP", "엣프피");


    private String mbti;
    private String discription;

    MbtiType(String mbti, String discription) {
        this.mbti = mbti;
        this.discription = discription;
    }

    public String getMbti() {
        return mbti;
    }

    public String getDiscription() {
        return discription;
    }
}
