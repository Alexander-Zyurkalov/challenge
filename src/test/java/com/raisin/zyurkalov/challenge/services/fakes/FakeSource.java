package com.raisin.zyurkalov.challenge.services.fakes;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import com.raisin.zyurkalov.challenge.services.Source;
import reactor.core.publisher.Flux;

public class FakeSource implements Source {
    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        ChallengeRecord[] records = {
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c1", Status.OK),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c2", Status.OK),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c3", Status.OK),
                new ChallengeRecord("5eb8c026a5a4c2dea2e6f010155a04c4", Status.OK),
        };
        return Flux.fromArray(records);
    }

    @Override
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {

    }
}
