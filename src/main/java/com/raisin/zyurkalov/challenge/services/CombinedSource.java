package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CombinedSource implements Source{
    private ExceptionsHolder exceptionHolder;
    private Source sourceA;
    private Source sourceB;

    public CombinedSource() {
    }

    public CombinedSource(Source sourceA,
                          Source sourceB) {
        this.sourceA = sourceA;
        this.sourceB = sourceB;
    }

    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        if (sourceA == null)
            return Flux.empty();
        Flux<ChallengeRecord> flux = sourceA.getChallengeRecords();
        if (sourceB != null)
            return flux.mergeWith(sourceB.getChallengeRecords());
        return flux;
    }

    @Autowired
    @Override
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionHolder = exceptionsHolder;
        if (this.sourceA != null)
            this.sourceA.setExceptionHolder(exceptionHolder);
        if (this.sourceB != null)
            this.sourceB.setExceptionHolder(exceptionHolder);
    }

    @Autowired
    @Qualifier("getSourceA")
    public void setSourceA(Source source1) {
        this.sourceA = source1;
    }

    @Autowired
    @Qualifier("getSourceB")
    public void setSourceB(Source source2) {
        this.sourceB = source2;
    }
}
