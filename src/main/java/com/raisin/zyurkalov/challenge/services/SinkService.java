package com.raisin.zyurkalov.challenge.services;

import com.raisin.zyurkalov.challenge.entities.Status;
import com.raisin.zyurkalov.challenge.services.sink_service.Response;
import com.raisin.zyurkalov.challenge.services.sink_service.Result;
import org.springframework.web.reactive.function.client.WebClient;

public class SinkService {
    final private String url;

    public SinkService(String url) {
        this.url = url;
    }


    public void sendResult(Kind kind, String id) {
        Result result = new Result(kind, id);
        Response response = WebClient.create(url).post().body(result, Result.class).retrieve()
                .bodyToMono(Response.class).block();
        if (response.getStatus() == Status.OK) {
            System.out.println("ой-ой!");
        }
    }

}
