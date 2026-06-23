package org.pytch.backend;

public enum PytchProgramKind {
    perMethod("per-method"),
    flat("flat");

    public final String kind;

    PytchProgramKind(String s) {
        kind = s;
    }
}
