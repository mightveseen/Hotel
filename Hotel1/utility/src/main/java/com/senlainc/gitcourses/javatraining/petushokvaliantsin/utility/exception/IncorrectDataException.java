package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception;

public class IncorrectDataException extends RuntimeException {

    public IncorrectDataException(String message) {
        super(message);
    }

    public IncorrectDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
