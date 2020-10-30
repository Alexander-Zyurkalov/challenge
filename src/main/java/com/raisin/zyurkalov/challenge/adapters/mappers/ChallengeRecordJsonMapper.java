package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import com.raisin.zyurkalov.challenge.services.ExceptionsHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordJsonMapper implements ChallengeRecordMapper {

    static ObjectMapper mapper = new ObjectMapper();
    ExceptionsHolder exceptionsHolder;

    /**
     * the main method to map strings to objects.
     * @param str
     * @return ChallengeRecord is the generic DTO for both XML and JSON payloads.
     */
    @Override
    public ChallengeRecord mapToObject(String str) {
        try {
            return mapper.readValue(str, ChallengeRecord.class);
        } catch (JsonProcessingException e) {
            exceptionsHolder.addException(e);
        }
        return new ChallengeRecord("FAIL", Status.FAIL);
    }

    @Override
    public ExceptionsHolder getExceptionsHolder() {
        return exceptionsHolder;
    }

    /**
     * This method supposed to be used for autowiring.
     * @param exceptionsHolder
     */
    @Autowired
    @Override
    public void setExceptionsHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }
}
