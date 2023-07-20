package com.Zizon.MBTInstagram.global.exception;

public class NoAccountException extends CustomException{
    public NoAccountException() {
        super("존재하지 않는 계정입니다.", 404);
    }
}
