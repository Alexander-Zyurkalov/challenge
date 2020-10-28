package com.raisin.zyurkalov.challenge;

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

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(ChallengeApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @Bean
    CommandLineRunner run() {
        return (evt) -> {
            System.out.println("OK");
        };
    }

}
