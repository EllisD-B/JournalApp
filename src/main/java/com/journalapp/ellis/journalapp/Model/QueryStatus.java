package com.journalapp.ellis.journalapp.Model;

public enum QueryStatus {
    SUCCESS("success"),

    FAILURE("failure");

    private final String id;

    QueryStatus(String id) {
        this.id = id;
    }
}
