package com.Zizon.MBTInstagram.global.exception;

public class LessThanTwoException extends CustomException{

    public LessThanTwoException() {
        super("궁합 비교 계정이 2개 미만입니다.", 400);
    }
}
