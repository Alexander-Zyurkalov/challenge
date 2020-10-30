package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.services.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeApplication {

    @Qualifier("getSource")
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
            source.getChallengeRecords().subscribe(System.out::println);
            exceptionsHolder.getExceptions().subscribe(System.out::println);
            exceptionsHolder.awaitAndShutdown();

        };

    }

}
