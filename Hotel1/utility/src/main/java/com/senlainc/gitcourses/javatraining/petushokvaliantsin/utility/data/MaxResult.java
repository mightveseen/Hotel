package com.senlainc.gitcourses.javatraining.petushokvaliantsin.utility.data;

public enum MaxResult {

    ATTENDANCE(40),
    ORDER(50),
    GUEST(50),
    ROOM(50);

    private final int variable;

    MaxResult(int variable) {
        this.variable = variable;
    }

    public int getMaxResult() {
        return this.variable;
    }
}
