package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import reactor.core.publisher.Flux;

public interface Source {
    public Flux<ChallengeRecord> getChallengeRecords();

    public void setExceptionHolder(ExceptionsHolder exceptionsHolder);
}
