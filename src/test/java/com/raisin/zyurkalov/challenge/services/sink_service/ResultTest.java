package com.raisin.zyurkalov.challenge.services.sink_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.raisin.zyurkalov.challenge.entities.Kind;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResultTest {

    @Test
    void convertToString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Result result = new Result(Kind.ORPHANED, "12345");
        String str = mapper.writeValueAsString(result);
        assertEquals("{\"kind\":\"orphaned\",\"id\":\"12345\"}", str);
    }

}