package com.raisin.zyurkalov.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.Disposable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ChallengeRecordMapperTest {


    static ChallengeRecordMapper jsonMapper = new ChallengeRecordJsonMapper();
    static ChallengeRecordMapper xmlMapper = new ChallengeRecordXmlMapper();

    private static Stream<Arguments> provideArguments() {
        return Stream.of(

//                Arguments.of(
//                        "{\"id\":\"841ed78790c9224d1643bf3d45ae14b9\",\"status\":\"ok\"}",
//                        jsonMapper
//                ),

                Arguments.of(
                        "<msg><id value=\"841ed78790c9224d1643bf3d45ae14b9\"/></msg>",
                        xmlMapper
                )
        );
    }


    @ParameterizedTest()
    @MethodSource("provideArguments")
    void converting(String rawData, ChallengeRecordMapper mapper) {
        ChallengeRecord record = mapper.mapToObject(rawData);
        assertEquals("841ed78790c9224d1643bf3d45ae14b9", record.getId(), "Id is converted");
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

        assertEquals(3,atomicInteger.get(), "We expect to have 3 exceptions of JsonProcessingException");



    }
}