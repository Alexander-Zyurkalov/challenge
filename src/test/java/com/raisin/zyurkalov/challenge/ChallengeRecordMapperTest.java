package com.raisin.zyurkalov.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ChallengeRecordMapperTest {





    @Test
    void converting() {

        ChallengeRecordMapper jsonMapper = new ChallengeRecordJsonMapper();
        ChallengeRecordMapper xmlMapper = new ChallengeRecordXmlMapper();

        ChallengeRecord record = jsonMapper.mapToObject("{\"id\":\"841ed78790c9224d1643bf3d45ae14b9\",\"status\":\"ok\"}");
        assertEquals("841ed78790c9224d1643bf3d45ae14b9", record.getId(), "Id is converted");
        assertEquals(Status.OK, record.getStatus(), "Status is OK");

        record = xmlMapper.mapToObject("<msg><id value=\"841ed78790c9224d1643bf3d45ae14b9\"/></msg>");
        assertEquals("841ed78790c9224d1643bf3d45ae14b9", record.getId(), "Id is converted");
        assertEquals(Status.OK, record.getStatus(), "Status is OK");

        record = xmlMapper.mapToObject("<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>");
        assertEquals("", record.getId(), "Id is converted");
        assertEquals(Status.DONE, record.getStatus(), "Status is done");



    }

    @Test
    void handlingErrorsTest() {
        ExceptionsHolder exceptionsHolder = new ExceptionsHolder();

        final AtomicInteger atomicInteger = new AtomicInteger();
        Disposable disposable = exceptionsHolder.getExceptions()
                .filter(e -> e instanceof JsonProcessingException)
                .subscribe((Exception x) -> atomicInteger.incrementAndGet());

        var jsonMapper = new ChallengeRecordJsonMapper();
        jsonMapper.setExceptionsHolder(exceptionsHolder);
        jsonMapper.mapToObject("{");
        jsonMapper.mapToObject("{ffff}");
        jsonMapper.mapToObject("{raw}Data");

        exceptionsHolder.awaitAndShutdown();

        assertEquals(3, atomicInteger.get(), "We expect to have 3 exceptions of JsonProcessingException");



    }
}