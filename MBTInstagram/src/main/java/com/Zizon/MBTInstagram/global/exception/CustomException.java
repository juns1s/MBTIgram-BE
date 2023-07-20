package com.Zizon.MBTInstagram.global.exception;

public abstract class CustomException extends Exception{
    private int httpStatus;

    public CustomException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
    public String getMessage(){
        return super.getMessage();
    }
}
