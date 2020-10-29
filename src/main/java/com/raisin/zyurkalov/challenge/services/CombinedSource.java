package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CombinedSource implements Source{
    private ExceptionsHolder exceptionHolder;
    final private Source source;
    final private Source sources[];

    public CombinedSource(Source source, Source... sources) {
        this.source = source;
        this.sources = sources;
    }

    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        Flux<ChallengeRecord> flux = source.getChallengeRecords();
        for (int i = 0; i < sources.length; i++) {
            flux.zipWith(sources[i].getChallengeRecords());
        }
        return flux;
    }

    @Autowired
    @Override
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionHolder = exceptionsHolder;
    }
}
