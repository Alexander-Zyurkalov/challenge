package com.raisin.zyurkalov.challenge.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {
    @JsonProperty("ok")
    OK("ok"),

    @JsonProperty("fail")
    FAIL("done"),

    @JsonProperty("done")
    DONE("done");

    private final String value;


    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
