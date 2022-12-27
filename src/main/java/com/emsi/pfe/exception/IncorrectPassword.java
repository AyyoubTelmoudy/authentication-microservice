package com.emsi.pfe.exception;

public class IncorrectPassword extends Exception{
    public IncorrectPassword(String errorMessage) {
        super(errorMessage);
    }
}
