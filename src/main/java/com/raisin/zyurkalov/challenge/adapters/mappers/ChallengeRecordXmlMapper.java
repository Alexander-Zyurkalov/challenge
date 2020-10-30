package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.xml_entities.Id;
import com.raisin.zyurkalov.challenge.adapters.mappers.xml_entities.XmlChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordXmlMapper implements ChallengeRecordMapper {

    static ObjectMapper mapper = new XmlMapper();

    ExceptionsHolder exceptionsHolder;

    @Override
    public String mapToString(ChallengeRecord object) {
        XmlChallengeRecord xmlChallengeRecord = toXmlChallengeRecord(object);
        try {
            return mapper.writeValueAsString(xmlChallengeRecord);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ChallengeRecord mapToObject(String str) {
        try {
            XmlChallengeRecord xmlChallengeRecord = mapper.readValue(str, XmlChallengeRecord.class);
            return toChallengeRecord(xmlChallengeRecord);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ChallengeRecord("FAIL", Status.FAIL);
    }

    @Override
    public ExceptionsHolder getExceptionsHolder() {
        return exceptionsHolder;
    }

    private ChallengeRecord toChallengeRecord(XmlChallengeRecord xmlChallengeRecord) {
        Status status = xmlChallengeRecord.getDone() == null ? null : Status.valueOf(xmlChallengeRecord.getDone());
        return new ChallengeRecord(xmlChallengeRecord.getId().value, status);
    }

    private XmlChallengeRecord toXmlChallengeRecord(ChallengeRecord object) {
        String status = object.getStatus() == null? "" : object.getStatus().toString();
        return new XmlChallengeRecord(
                new Id(object.getId()), status
        );
    }

    @Autowired
    public void setExceptionsHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }


}
