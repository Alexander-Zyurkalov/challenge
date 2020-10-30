package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

public class SolutionService {

    private Set<ChallengeRecord> orphans = new CopyOnWriteArraySet<>();
    private Set<ChallengeRecord> joined = new CopyOnWriteArraySet<>();

    public void addRecord(ChallengeRecord record) {
        if (orphans.contains(record)) {
            orphans.remove(record);
            joined.add(record);
        }
        else {
            orphans.add(record);
        }
    }

    public Stream<ChallengeRecord> processOrphans() {
        return orphans.stream().peek(record -> {
            orphans.remove(record);
        });
    }

    public Stream<ChallengeRecord> processJointed() {
        return joined.stream().peek(record -> {
            joined.remove(record);
        });
    }
}
