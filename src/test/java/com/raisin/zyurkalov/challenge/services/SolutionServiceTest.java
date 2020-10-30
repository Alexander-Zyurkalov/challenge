package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SolutionServiceTest {

    @Test
    void addRecord() {
        var records = Stream.of(
                new ChallengeRecord("1", Status.OK),
                new ChallengeRecord("2", Status.OK),
                new ChallengeRecord("1", Status.OK),
                new ChallengeRecord("5", Status.OK),
                new ChallengeRecord("6", Status.OK),
                new ChallengeRecord("3", Status.OK),
                new ChallengeRecord("5", Status.OK),
                new ChallengeRecord("55", Status.OK)
        );
        SolutionService solutionService = new SolutionService();

        records.forEach(solutionService::addRecord);
        assertEquals(
                List.of("1", "5"),
                solutionService.getJoined().map(ChallengeRecord::getId).sorted().collect(Collectors.toList())
        );
        assertEquals(
                List.of("2", "3", "55", "6"),
                solutionService.getOrphans().map(ChallengeRecord::getId).sorted().collect(Collectors.toList())
        );
    }
}
