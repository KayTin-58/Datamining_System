package com.zhang.exception;

/**
 * Created by 直到世界尽头 on 2018/4/9.
 */
public class HeadersNotequealException extends Exception {
    private String errorMessage;

    public HeadersNotequealException(String errorMessage){
        super(errorMessage);
        this.errorMessage=errorMessage;
    }

    @Override
    public String toString() {
        return "HeadersNotequealException{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
