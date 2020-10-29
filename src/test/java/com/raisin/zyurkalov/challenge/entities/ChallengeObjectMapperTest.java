package com.raisin.zyurkalov.challenge.entities;

import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeObjectMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ChallengeObjectMapperTest {



    static ChallengeObjectMapper jsonMapper = new ChallengeRecordJsonMapper();
    static ChallengeObjectMapper xmlMapper = new ChallengeRecordXmlMapper();

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of("{\"id\":\"841ed78790c9224d1643bf3d45ae14b9\",\"status\":\"ok\"}", jsonMapper),
                Arguments.of(
                        "<msg><id value=\"841ed78790c9224d1643bf3d45ae14b9\"/></msg>", xmlMapper)
        );
    }


    @ParameterizedTest
    @MethodSource("provideArguments")
    void converting(String rawData, ChallengeObjectMapper mapper) {
        ChallengeRecord record = mapper.mapToObject(rawData);
        assertEquals("841ed78790c9224d1643bf3d45ae14b9", record.getId(), "Id is converted");

        String returnedRawData = mapper.mapToString(record);
        assertEquals(rawData, returnedRawData, "The json string converted back is the same as the original");

    }
}