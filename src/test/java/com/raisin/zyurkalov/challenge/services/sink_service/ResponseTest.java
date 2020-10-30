package com.raisin.zyurkalov.challenge.services.sink_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.zyurkalov.challenge.entities.Status;
import com.raisin.zyurkalov.challenge.entities.sink_service.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseTest {

    @Test
    void convertFromStr() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String str = "{\"status\": \"ok\"}";
        Response response = mapper.readValue(str, Response.class);
        assertEquals(Status.OK, response.getStatus());

        str = "{\"status\": \"fail\"}";
        response = mapper.readValue(str, Response.class);
        assertEquals(Status.FAIL, response.getStatus());

    }
}