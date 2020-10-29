package com.raisin.zyurkalov.challenge.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChallengeRecordTest {

    @Test
    void converting() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"id\":\"841ed78790c9224d1643bf3d45ae14b9\",\"status\":\"ok\"}";

        ChallengeRecord record = mapper.readValue(json, ChallengeRecord.class);
        assertEquals("ok", record.getStatus().toString(), "Status is converted ");
        assertEquals("841ed78790c9224d1643bf3d45ae14b9", record.getId(), "Id is converted");

        String returnedJson = mapper.writeValueAsString(record);
        assertEquals(json, returnedJson, "The json string converted back is the same as the original");


    }
}