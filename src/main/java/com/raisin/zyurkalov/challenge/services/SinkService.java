package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.Kind;
import com.raisin.zyurkalov.challenge.services.sink_service.Response;
import com.raisin.zyurkalov.challenge.services.sink_service.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Class to send data to fixture3.
 */
@Component
public class SinkService {
    final private String url;
    private ExceptionsHolder exceptionsHolder;

       public SinkService(@Autowired(required = false) @Qualifier("getUrlSink") String url) {
           this.url = url;
        }


    public Response sendResult(Kind kind, String id) {
        Result result = new Result(kind, id);
        try {
            Response response = WebClient.create(url).post().body(Mono.just(result), Result.class).retrieve()
                    .bodyToMono(Response.class).block();
            return response;
        } catch (Exception e) {
            exceptionsHolder.addException(e);
        }
        return null;
    }

    @Autowired
    public void setExceptionsHolder(ExceptionsHolder exceptionsHolder) {
        this.exceptionsHolder = exceptionsHolder;
    }


}
