package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.SortedMap;

@AllArgsConstructor
public class JsonSource implements Source {

    private String url;

    @Override
    public Flux<ChallengeRecord> getChallengeRecords() {
        return WebClient.create(url).get().retrieve().bodyToFlux(ChallengeRecord.class);
    }
}
