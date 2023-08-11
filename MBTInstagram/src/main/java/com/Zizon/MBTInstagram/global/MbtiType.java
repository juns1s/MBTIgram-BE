package com.Zizon.MBTInstagram.global;

import java.util.HashMap;
import java.util.Map;

public enum MbtiType {

    ISTJ("ISTJ"),
    ISFJ("ISFJ"),
    INFJ("INFJ"),
    INTJ("INTJ"),
    ISTP("ISTP"),
    ISFP("ISFP"),
    INFP("INFP"),
    INTP("INTP"),
    ESTP("ESTP"),
    ESFP("ESFP"),
    ENFP("ENFP"),
    ENTP("ENTP"),
    ESTJ("ESTJ"),
    ESFJ("ESFJ"),
    ENFJ("ENFJ"),
    ENTJ("ENTJ");

    public final String mbti;
    MbtiType(String type) {
        this.mbti = type;
    }

    private static final Map<String, MbtiType> stringToEnum = new HashMap<>();

    static {
        for (MbtiType mbtiType : values()) {
            stringToEnum.put(mbtiType.mbti, mbtiType);
        }
    }

    public static MbtiType fromString(String type) {
        return stringToEnum.get(type);
    }
}
