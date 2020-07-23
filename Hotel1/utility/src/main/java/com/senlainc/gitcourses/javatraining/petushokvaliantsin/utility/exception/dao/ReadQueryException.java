package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception.dao;

public class ReadQueryException extends RuntimeException {

    public ReadQueryException(Throwable cause) {
        super(cause);
    }

    public ReadQueryException() {
        super();
    }

    public ReadQueryException(String message) {
        super(message);
    }
}
