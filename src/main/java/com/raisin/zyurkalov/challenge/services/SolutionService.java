package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class SolutionService {
    private Set<ChallengeRecord> orphans = new HashSet<>();
    private List<ChallengeRecord> joined = new ArrayList<>();

    public void addRecord(ChallengeRecord record) {
        if (orphans.contains(record)) {
            orphans.remove(record);
            joined.add(record);
        }
        else {
            orphans.add(record);
        }
    }

    public Stream<ChallengeRecord> getOrphans() {
        return orphans.stream();
    }

    public Stream<ChallengeRecord> getJoined() {
        return joined.stream();
    }
}
