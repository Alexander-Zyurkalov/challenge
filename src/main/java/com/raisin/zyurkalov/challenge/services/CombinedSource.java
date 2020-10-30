package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

/**
 * This clas combines to sources, both A and B into one stream so that we can have entities form endpoint A and then
 * from endpoint B one after another
 */
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
            return flux.zipWith(sourceB.getChallengeRecords())
                    .flatMap(
                            (Tuple2<ChallengeRecord,ChallengeRecord> tuple2) -> Flux.fromArray(tuple2.toArray()))
                    .map(o -> (ChallengeRecord)o);
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
