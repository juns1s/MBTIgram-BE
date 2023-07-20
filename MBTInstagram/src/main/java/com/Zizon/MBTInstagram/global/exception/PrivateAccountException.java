package com.Zizon.MBTInstagram.global.exception;

public class PrivateAccountException extends CustomException{
    public PrivateAccountException() {
        super("비공개 계정입니다.", 401);
    }

}
