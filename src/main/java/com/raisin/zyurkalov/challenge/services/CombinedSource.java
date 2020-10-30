package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This clas combines to sources, both A and B into one stream so that we can have entities form endpoint A and then
 * from endpoint B one after another
 */
@Component
public class CombinedSource implements Source{
    private ExceptionsHolder exceptionHolder;
    private ConcreteSource sourceA;
    private ConcreteSource sourceB;

    public CombinedSource() {
    }

    public CombinedSource(ConcreteSource sourceA,
                          ConcreteSource sourceB) {
        this.sourceA = sourceA;
        this.sourceB = sourceB;
    }

    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        List<ConcreteSource> list = new ArrayList<ConcreteSource>();
        if (sourceA != null) list.add(sourceA);
        if (sourceB != null) list.add(sourceB);
        var done = new HashSet<ConcreteSource>();
        return Flux.create(
                x -> {
                    ChallengeRecord record = null;
                    do {
                        for (ConcreteSource source: list) {
                            if (done.contains(source)) continue;
                            try {
                                record = source.getChallengeRecords().blockOptional().orElse(
                                        new ChallengeRecord("", Status.FAIL)
                                );
                                if (record.getStatus() != Status.DONE)
                                    x.next(record);
                            } catch (Exception e) {
                                exceptionHolder.addException(e);
                                x.complete();
                            }
                            if (record.getStatus() == Status.DONE)
                                done.add(source);
                        }

                    } while (!x.isCancelled() && done.size() < list.size());
                    x.complete();
                },
                FluxSink.OverflowStrategy.BUFFER
        );
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
    public void setSourceA(ConcreteSource source1) {
        this.sourceA = source1;
    }

    @Autowired
    @Qualifier("getSourceB")
    public void setSourceB(ConcreteSource source2) {
        this.sourceB = source2;
    }
}
