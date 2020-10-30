package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ConcreteSource {

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
    public void setExceptionHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }


    public Mono<ChallengeRecord> getChallengeRecords() {

        return requestToUrl().map(mapper::mapToObject);
    }

    protected Mono<String> requestToUrl() {
        return WebClient.create(url).get().retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcreteSource)) return false;

        ConcreteSource source = (ConcreteSource) o;

        return url != null ? url.equals(source.url) : source.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
