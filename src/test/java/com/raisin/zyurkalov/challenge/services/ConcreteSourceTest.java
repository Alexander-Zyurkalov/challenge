package com.raisin.zyurkalov.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raisin.zyurkalov.challenge.ChallengeApplicationConfiguration;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConcreteSourceTest {

    @Test
    void getChallengeRecords() {


        var exceptionsHolder = new ExceptionsHolder();
        final AtomicInteger atomicInteger = new AtomicInteger();
        Disposable disposable = exceptionsHolder.getExceptions()
                .filter(e -> e instanceof JsonProcessingException)
                .subscribe((Exception x) -> atomicInteger.incrementAndGet());


        ConcreteSource concreteSource = new ConcreteSource(ChallengeApplicationConfiguration.URL_A);
        concreteSource.setExceptionHolder(exceptionsHolder);
        var mapper = new ChallengeRecordJsonMapper();
        mapper.setExceptionsHolder(exceptionsHolder);
        concreteSource.setMapper(mapper);
        List<ChallengeRecord> list = concreteSource.getChallengeRecords().collectList().blockOptional().get();

        assertNotNull(list);
        assertTrue(atomicInteger.get() > 1, "We have json parsing errors");

    }
}