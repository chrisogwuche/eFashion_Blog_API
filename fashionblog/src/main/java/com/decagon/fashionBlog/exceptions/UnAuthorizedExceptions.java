package com.decagon.fashionBlog.exceptions;

public class UnAuthorizedExceptions extends RuntimeException{

    public UnAuthorizedExceptions() {
        super();
    }

    public UnAuthorizedExceptions(String message) {
        super(message);
    }


    public UnAuthorizedExceptions(String message, Throwable cause) {
        super(message, cause);
    }


    public UnAuthorizedExceptions(Throwable cause) {
        super(cause);
    }

    protected UnAuthorizedExceptions(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
