package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExceptionsHandler {
    @Autowired
    ExceptionsHolder exceptionsHolder;

    public void handle() {
        exceptionsHolder.getExceptions().subscribe(
                (e) -> {
                    log.error("Exception happened: {}", e.getMessage());
                });
    }

    public void awaitAndShutdown(){
        exceptionsHolder.awaitAndShutdown();
    }

}
