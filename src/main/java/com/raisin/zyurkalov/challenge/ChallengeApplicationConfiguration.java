package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.services.ConcreteSource;
import com.raisin.zyurkalov.challenge.services.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChallengeApplicationConfiguration {

    public static final String URL_A = "http://localhost:7299/source/a";
    public static final String URL_B = "http://localhost:7299/source/b";


    @Bean
    public Source getSource() {
        return new ConcreteSource(URL_A);
    }


    @Bean
    public ExceptionsHolder getExceptionsHolder() {
        return new ExceptionsHolder();
    }

}
