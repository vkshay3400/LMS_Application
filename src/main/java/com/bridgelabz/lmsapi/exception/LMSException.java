package com.bridgelabz.lmsapi.exception;

public class LMSException extends RuntimeException{
    private static final long serialVersionUID = 5776681206288518465L;

    private exceptionType type;
    private String message;

    public LMSException(exceptionType type, String message) {
        this.type = type;
        this.message=message;
    }

    public enum exceptionType {
        USER_NOT_FOUND, INVALID_PASSWORD, INVALID_ID, INVALID_EMAIL_ID, DATA_NOT_FOUND;
    }
}
