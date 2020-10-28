package com.raisin.zyurkalov.challenge.services.fakes;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.services.Source;
import reactor.core.publisher.Flux;

public class FakeSource implements Source {
    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        ChallengeRecord[] records = {
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c1", "ok", null),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c2", "ok", null),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c3", "ok", null),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c4", "ok", null),
        };
        return Flux.fromArray(records);
    }
}
