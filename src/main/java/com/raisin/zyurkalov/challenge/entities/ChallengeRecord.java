package com.raisin.zyurkalov.challenge.entities;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JacksonXmlRootElement(localName = "msg")
public class ChallengeRecord {

    @JacksonXmlElementWrapper(localName = "id111")
    private String id;
    private Status status;
}
