package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.entities.ChallengeRecord;
import com.raisin.zyurkalov.challenge.services.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@SpringBootApplication
public class ChallengeApplication {

    @Autowired
    Source source;

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(ChallengeApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    CommandLineRunner run() {
        return (evt) -> {
            Mono<List<ChallengeRecord>> monoRecords = source.getChallengeRecords().collectList();
            List<ChallengeRecord> records = monoRecords.block(Duration.ofMillis(10000));
            records.forEach(record -> {
                System.out.println(record);
            });

        };
    }

}
