package com.zhang.exception;

/**
 * Created by 直到世界尽头 on 2018/4/6.
 */
public class TypeException extends Exception  {

    private String errorMessage;

    public TypeException(String errorMessage){
        super(errorMessage);
        this.errorMessage=errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
