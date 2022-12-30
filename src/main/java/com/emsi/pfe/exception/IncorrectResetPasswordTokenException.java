package com.emsi.pfe.exception;


public class IncorrectResetPasswordTokenException extends Exception{
    public IncorrectResetPasswordTokenException(String errorMessage) {
        super(errorMessage);
    }
}

