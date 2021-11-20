package edu.ntut.project_01.homegym.exception.category;

public class MemberNotExistException extends RuntimeException{
    public MemberNotExistException() {
        super();
    }

    public MemberNotExistException(String message) {
        super(message);
    }

    public MemberNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberNotExistException(Throwable cause) {
        super(cause);
    }

    protected MemberNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
