package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.console;

public enum ConsoleColor {

    GREEN("\u001B[92m"),
    RED("\u001B[91m"),
    YELLOW("\u001B[93m"),
    RESET("\u001B[0m");

    private final String code;

    ConsoleColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
