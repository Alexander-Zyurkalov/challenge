package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import com.raisin.zyurkalov.challenge.services.CombinedSource;
import com.raisin.zyurkalov.challenge.services.ConcreteSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChallengeApplicationConfiguration {

    public static final String URL_A = "http://localhost:7299/source/a";
    public static final String URL_B = "http://localhost:7299/source/b";


    @Bean
    public CombinedSource getSource() {
        return new CombinedSource(getSourceA(),getSourceB());
    }

    public ConcreteSource getSourceA(){
        var source = new ConcreteSource(URL_A);
        source.setMapper(new ChallengeRecordJsonMapper());
        return  source;
    }

    public ConcreteSource getSourceB(){
        var source = new ConcreteSource(URL_B);
        source.setMapper(new ChallengeRecordXmlMapper());
        return  source;
    }

    @Bean
    public ExceptionsHolder getExceptionsHolder() {
        return new ExceptionsHolder();
    }

}
