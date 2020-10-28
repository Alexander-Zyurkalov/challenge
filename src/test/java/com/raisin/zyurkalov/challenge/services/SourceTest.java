package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.services.fakes.FakeSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;




class SourceTest {

    @Test
    void getChallengeRecords() {
        var source = new FakeSource();
        assertTrue(
                source.getChallengeRecords().all(
                        challengeRecord -> challengeRecord.getStatus().equals("ok")
                ).blockOptional().get()
        );
    }
}