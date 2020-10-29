package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.xml_entities.Id;
import com.raisin.zyurkalov.challenge.adapters.mappers.xml_entities.XmlChallengeRecord;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import org.springframework.stereotype.Component;

@Component
public class ChallengeRecordXmlMapper implements ChallengeObjectMapper {

    static ObjectMapper mapper = new XmlMapper();


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
        return null;
    }

    private ChallengeRecord toChallengeRecord(XmlChallengeRecord xmlChallengeRecord) {
        return new ChallengeRecord(xmlChallengeRecord.getId().value, null);
    }

    private XmlChallengeRecord toXmlChallengeRecord(ChallengeRecord object) {
        return new XmlChallengeRecord(
                new Id(object.getId())
        );
    }

}
