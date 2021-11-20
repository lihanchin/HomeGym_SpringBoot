package edu.ntut.project_01.homegym.exception.category;


public class VerificationMailException extends RuntimeException{

    public VerificationMailException() {
    }

    public VerificationMailException(String message) {
        super(message);
    }

    public VerificationMailException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerificationMailException(Throwable cause) {
        super(cause);
    }

    protected VerificationMailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
