package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.services.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

    @Autowired
    Source source;

    @Autowired
    ExceptionsHolder exceptionsHolder;

    @Autowired
    ChallengeRecordJsonMapper jsonMapper;


    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(ChallengeApplication.class).web(WebApplicationType.NONE).run(args);
    }



    @Bean
    CommandLineRunner run() {
        return (evt) -> {
            String rawData = "{";
            jsonMapper.mapToObject(rawData);
            jsonMapper.mapToObject("{ffff}");
            jsonMapper.mapToObject("{raw}Data");
            exceptionsHolder.getExceptions().subscribe(
                    e -> {
                        System.out.println(e);
                    }
            );
            exceptionsHolder.awaitAndShutdown();

        };

    }

}
