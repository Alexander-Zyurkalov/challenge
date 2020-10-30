package com.raisin.zyurkalov.challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FakeConcreteXmlSource extends ConcreteSource {

    public FakeConcreteXmlSource() {
        super("url");
    }

    List<String> list = new LinkedList(List.of(
            "<msg><id value=\"0affd6558f58850cdb518a686d6409a2\"/></msg>",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"0affd6558f58850cdb518a686d6409a1\"/></msg>",
            "status\": \"s\",  \"id\": \"0affd6558f58850cdb518a686d6409a4\"}",
            ""
    ));

    @Override
    protected Mono<String> requestToUrl() {
        if (list.isEmpty())
            return Mono.just("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>");
        else
            return Mono.just(list.remove(0));
    }
}

class ConcreteXmlSourceTest {

    @Test
    void getChallengeRecords() {
        var exceptionsHolder = new ExceptionsHolder();
        final AtomicInteger atomicInteger = new AtomicInteger();
        Disposable disposable = exceptionsHolder.getExceptions()
                .filter(e -> e instanceof JsonProcessingException)
                .subscribe((Exception x) -> atomicInteger.incrementAndGet());

        ConcreteSource concreteSource = new FakeConcreteXmlSource();


        concreteSource.setExceptionHolder(exceptionsHolder);
        var mapper = new ChallengeRecordXmlMapper();
        mapper.setExceptionsHolder(exceptionsHolder);
        concreteSource.setMapper(mapper);

        CombinedSource combinedSource = new CombinedSource(concreteSource);
        combinedSource.setExceptionHolder(exceptionsHolder);

        List<ChallengeRecord> list = combinedSource.getChallengeRecords().collectList().blockOptional().get();

        assertEquals(4, list.size(), "Number of returned records is 3");
        assertEquals(2, atomicInteger.get() , "We have json parsing errors");

    }
}