package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;

public interface ChallengeObjectMapper {
    String mapToString(ChallengeRecord object);

    ChallengeRecord mapToObject(String str);

    ExceptionsHolder getExceptionsHolder();

    void setExceptionsHolder(ExceptionsHolder exceptionsHolder);


}
