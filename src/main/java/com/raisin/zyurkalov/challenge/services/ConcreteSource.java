package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

@Component
public class ConcreteSource implements Source {

    private String url;

    public ConcreteSource(@Autowired(required = false) String url) {
        this.url = url;
    }

    ChallengeRecordMapper mapper;
    ExceptionsHolder exceptionsHolder;

    public void setMapper(ChallengeRecordMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    @Override
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }


    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {

        return Flux.create(
                x -> {
                    ChallengeRecord record = null;
                    do {
                        try {
                            record = requestToUrl()
                                    .map(str -> mapper.mapToObject(str))
                                    .blockOptional().orElse(
                                            new ChallengeRecord("", Status.FAIL)
                                    );
                            if (record.getStatus() != Status.DONE)
                                x.next(record);
                        } catch (Exception e) {
                            exceptionsHolder.addException(e);
                            x.complete();
                        }
                    } while (!x.isCancelled() && record != null && record.getStatus() != Status.DONE);
                    x.complete();
                },
                FluxSink.OverflowStrategy.BUFFER
        );
    }

    protected Mono<String> requestToUrl() {
        return WebClient.create(url).get().retrieve()
                .bodyToMono(String.class);
    }


}
