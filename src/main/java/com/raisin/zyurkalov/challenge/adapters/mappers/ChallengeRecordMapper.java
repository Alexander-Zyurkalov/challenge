package com.raisin.zyurkalov.challenge.adapters.mappers;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;

/**
 * Since we deal with xml and json and in order to share code to handle xml- and json-payloads, I decided to add
 * adapters for converting json and xml to objects. It also allows to catch exceptions and send them to
 * @see ExceptionsHolder
 */
public interface ChallengeRecordMapper {

    /**
     * the main method to map strings to objects.
     * @param str
     * @return ChallengeRecord is the generic DTO for both XML and JSON payloads.
     */
    ChallengeRecord mapToObject(String str);


    /**
     * returns the attached exception Holder
     * @return
     */
    ExceptionsHolder getExceptionsHolder();

    /**
     * This method supposed to be used for autowiring.
     * @param exceptionsHolder
     */
    void setExceptionsHolder(ExceptionsHolder exceptionsHolder);


}
