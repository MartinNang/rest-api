package org.pytch.backend;

public enum PytchProjectStatus {
    unlisted("unlisted"),
    listed("listed");

    public final String status;

    PytchProjectStatus(String s) {
        status = s;
    }
}
