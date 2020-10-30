package com.raisin.zyurkalov.challenge.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Kind {
    @JsonProperty("joined")
    JOINED("joined"),

    @JsonProperty("orphaned")
    ORPHANED("orphaned");

    private String value;

    Kind(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
