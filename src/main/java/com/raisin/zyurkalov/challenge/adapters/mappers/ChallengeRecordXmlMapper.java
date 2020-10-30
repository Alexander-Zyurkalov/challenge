package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordXmlMapper implements ChallengeRecordMapper {

    static ObjectMapper mapper = new XmlMapper();

    ExceptionsHolder exceptionsHolder;

    @Override
    public ChallengeRecord mapToObject(String str) {
        try {
            JsonNode tree = mapper.readTree(str);
            String id = "";
            if (tree.has("id") && tree.findValue("id").has("value"))
                id = tree.findValue("id").findValue("value").asText();
            Status status = tree.has("done") ? Status.DONE : Status.OK;
            return new ChallengeRecord(id, status);
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
    public void setExceptionsHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }


}
