package com.raisin.zyurkalov.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FakeConcreteJsonSource extends ConcreteSource {

    public FakeConcreteJsonSource() {
        super("url");
    }

    List<String> list = new LinkedList(List.of(
            "{\"status\": \"ok\",  \"id\": \"0affd6558f58850cdb518a686d6409a1\"}",
            "{\"status\": \"ok\",  \"id\": \"0affd6558f58850cdb518a686d6409a2\"}",
            "status\": \"s\",  \"id\": \"0affd6558f58850cdb518a686d6409a4\"}"
    ));

    @Override
    protected Mono<String> requestToUrl() {
        if (list.isEmpty())
            return Mono.just("{\"status\": \"done\",  \"id\": \"0affd6558f58850cdb518a686d6409a4\"}");
        else
            return Mono.just(list.remove(0));
    }
}

class ConcreteJsonSourceTest {

    @Test
    void getChallengeRecords() {
        var exceptionsHolder = new ExceptionsHolder();
        final AtomicInteger atomicInteger = new AtomicInteger();
        Disposable disposable = exceptionsHolder.getExceptions()
                .filter(e -> e instanceof JsonProcessingException)
                .subscribe((Exception x) -> atomicInteger.incrementAndGet());

        ConcreteSource concreteSource = new FakeConcreteJsonSource();


        concreteSource.setExceptionHolder(exceptionsHolder);
        var mapper = new ChallengeRecordJsonMapper();
        mapper.setExceptionsHolder(exceptionsHolder);
        concreteSource.setMapper(mapper);

        CombinedSource combinedSource = new CombinedSource(concreteSource);
        combinedSource.setExceptionHolder(exceptionsHolder);

        List<ChallengeRecord> list = combinedSource.getChallengeRecords().collectList().blockOptional().get();

        assertEquals(3, list.size(), "Number of returned records is 3");
        assertTrue(atomicInteger.get() > 0, "We have json parsing errors");

    }
}