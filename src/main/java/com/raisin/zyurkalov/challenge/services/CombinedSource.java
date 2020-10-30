package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CombinedSource implements Source{
    private ExceptionsHolder exceptionHolder;
    private Source source1;
    private Source source2;

    public CombinedSource() {
    }

    public CombinedSource(Source source1,
                          Source source2) {
        this.source1 = source1;
        this.source2 = source2;
    }

    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        if (source1 == null)
            return Flux.empty();
        Flux<ChallengeRecord> flux = source1.getChallengeRecords();
        if (source2 != null)
            return flux.mergeWith(source2.getChallengeRecords());
        return flux;
    }

    @Autowired
    @Override
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionHolder = exceptionsHolder;
        if (this.source1 != null)
            this.source1.setExceptionHolder(exceptionHolder);
        if (this.source2 != null)
            this.source2.setExceptionHolder(exceptionHolder);
    }

    public void setSource1(Source source1) {
        this.source1 = source1;
    }

    public void setSource2(Source source2) {
        this.source2 = source2;
    }
}
