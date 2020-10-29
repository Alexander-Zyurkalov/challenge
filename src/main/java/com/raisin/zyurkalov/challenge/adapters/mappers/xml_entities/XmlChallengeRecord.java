package com.raisin.zyurkalov.challenge.adapters.mappers.xml_entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JacksonXmlRootElement(localName = "msg")
public class XmlChallengeRecord {
    @JacksonXmlProperty
    Id id;
}
