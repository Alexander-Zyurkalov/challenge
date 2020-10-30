package com.raisin.zyurkalov.challenge.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.WorkQueueProcessor;

import java.time.Duration;

/**
 * The goal of this class is to save all the exceptions in asynchronous manner and pass them to ExceptionsHandler
 * @see com.raisin.zyurkalov.challenge.services.ExceptionsHandler
 */
public class ExceptionsHolder {
    /**
     * WorkQueueProcessor is deprecated, but I used to used it for before and I didn't have time to study what I can
     * replace it with.
     */
    private final WorkQueueProcessor<Exception> exceptions;
    private final FluxSink<Exception> sink;

    public ExceptionsHolder() {
        exceptions = WorkQueueProcessor.<Exception>builder().build();
        sink = exceptions.sink();
    }

    /**
     * Adds an exception to the queue
     * @param e
     */
    public void addException(Exception e) {
        sink.next(e);
    }

    /**
     * Returns the Flux stream of exceptions to handle them asynchronously.
     * @return Flux<Exception>
     */
    public Flux<Exception> getExceptions() {
        return exceptions;
    }

    /**
     * Since the handler is asynchronous, it might be useful to call this method to wait before all the exceptions are
     * handled.
     */
    public void awaitAndShutdown(){
        exceptions.awaitAndShutdown(Duration.ofMinutes(1));
    }

}
