package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Stream;

/**
 * This is the class where all the logic solving the problem is.
 * The logic is simple. To find joined items it adds all of new items to orphans, and checks all other where there
 * duplicates. If there are duplications, there record is deleted from orphans and placed to joined.
 * When a record in "joined", it can already be sent to the Sink that we found the joined record.
 */
public class SolutionService {

    private Set<ChallengeRecord> orphans = new CopyOnWriteArraySet<>();
    private Set<ChallengeRecord> joined = new CopyOnWriteArraySet<>();

    /**
     * add records to the storage and decides to which category we can categorize it as mentioned in the description
     * of the class
     * @param record
     */
    public void addRecord(ChallengeRecord record) {
        if (orphans.contains(record)) {
            orphans.remove(record);
            joined.add(record);
        }
        else {
            orphans.add(record);
        }
    }

    /**
     * returns the number of orphans, it is used to check whether we need to send them to the Sink.
     * @return
     */
    public int getNumberOfOrphans() {
        return orphans.size();
    }

    /**
     * returns the number of Joineds, it is used to check whether we need to send them to the Sink.
     * @return
     */
    public int getNumberOfJoined() {
        return joined.size();
    }

    /**
     * returns all the orphans and removes them immediately from the storage.
     * @return
     */
    public Stream<ChallengeRecord> processOrphans() {
        return orphans.stream().peek(record -> {
            orphans.remove(record);
        });
    }

    /**
     * returns all the Joineds and removes them immediately from the storage.
     */
    public Stream<ChallengeRecord> processJointed() {
        return joined.stream().peek(record -> {
            joined.remove(record);
        });
    }
}
