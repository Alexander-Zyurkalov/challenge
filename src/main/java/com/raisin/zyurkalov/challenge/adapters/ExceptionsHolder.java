package com.raisin.zyurkalov.challenge.adapters;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;

import java.time.Duration;


public class ExceptionsHolder {
    private WorkQueueProcessor<Exception> exceptions;
    private FluxSink<Exception> sink;

    public ExceptionsHolder() {
        exceptions = WorkQueueProcessor.<Exception>builder().build();
        sink = exceptions.sink();

    }

    public void addException(Exception e) {
        sink.next(e);
    }

    public Flux<Exception> getExceptions() {
        return exceptions;
    }

    public void awaitAndShutdown(){
        exceptions.awaitAndShutdown(Duration.ofMinutes(1));
    }

}
