package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.exception;

public class ElementNotFoundException extends RuntimeException {

    public ElementNotFoundException(String message, Throwable stackTrace) {
        super(message, stackTrace);
    }

    public ElementNotFoundException(String message) {
        super(message);
    }
}
