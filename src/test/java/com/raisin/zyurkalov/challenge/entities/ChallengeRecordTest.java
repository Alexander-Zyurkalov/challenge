package com.raisin.zyurkalov.challenge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeRecordTest {

    @Test
    void getStatus() {
        var record = new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c6", "ok", null);
        assertEquals("ok", record.getStatus(), "We can instantiate ChallengeRecord");
    }
}