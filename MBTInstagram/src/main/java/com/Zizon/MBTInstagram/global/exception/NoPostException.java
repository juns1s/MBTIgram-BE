package com.Zizon.MBTInstagram.global.exception;

public class NoPostException extends CustomException{
    public NoPostException() {
        super("게시물이 없습니다.", 400);
    }
}
