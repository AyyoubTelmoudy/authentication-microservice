package com.emsi.pfe.exception;


public class EmailNotFoundException extends Exception{
    public EmailNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
