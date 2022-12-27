package com.emsi.pfe.exception;


public class RefreshTokenMissingException extends Exception{

    public RefreshTokenMissingException(String errorMessage) {
        super(errorMessage);
    }
}
