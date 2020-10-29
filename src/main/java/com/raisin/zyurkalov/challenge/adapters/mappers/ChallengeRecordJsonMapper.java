package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordJsonMapper implements ChallengeRecordMapper {

    static ObjectMapper mapper = new ObjectMapper();
    ExceptionsHolder exceptionsHolder;

    @Override
    public String mapToString(ChallengeRecord object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            exceptionsHolder.addException(e);
        }
        return null;
    }

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

    @Autowired
    @Override
    public void setExceptionsHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }
}
