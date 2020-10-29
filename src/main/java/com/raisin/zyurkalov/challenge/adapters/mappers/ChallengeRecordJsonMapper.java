package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordJsonMapper implements ChallengeObjectMapper {

    static ObjectMapper mapper = new ObjectMapper();

    @Override
    public String mapToString(ChallengeRecord object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ChallengeRecord mapToObject(String str) {
        try {
            return mapper.readValue(str, ChallengeRecord.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
