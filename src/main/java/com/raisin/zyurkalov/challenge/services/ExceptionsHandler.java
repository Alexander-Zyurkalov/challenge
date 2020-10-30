package com.raisin.zyurkalov.challenge.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * This is an exception handler. The main purpose of the class is to handle all the possible exceptions
 * in one place. For example, we can add here sending opsgeine alert, e-mail, or just writing to the logs.
 * */
@Component
@Slf4j
public class ExceptionsHandler {
    @Autowired
    ExceptionsHolder exceptionsHolder;

    /**
     * Write all the code here to handle the exceptions
     */
    public void handle() {
        exceptionsHolder.getExceptions().subscribe(
                (e) -> {
                    log.error("Exception happened: {}", e.getMessage());
                });
    }

    /**
     * Since the handler is asynchronous, it might be useful to call this method to wait before all the exceptions are
     * handled.
     */
    public void awaitAndShutdown(){
        exceptionsHolder.awaitAndShutdown();
    }

}
