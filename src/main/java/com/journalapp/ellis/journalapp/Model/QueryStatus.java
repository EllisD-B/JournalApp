package com.journalapp.ellis.journalapp.Model;

public enum QueryStatus {

    SUCCESS("success"),
    FAILURE("failure");

    private final String status;

    QueryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
