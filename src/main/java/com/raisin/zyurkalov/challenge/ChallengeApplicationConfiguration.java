package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordJsonMapper;
import com.raisin.zyurkalov.challenge.adapters.mappers.ChallengeRecordXmlMapper;
import com.raisin.zyurkalov.challenge.services.CombinedSource;
import com.raisin.zyurkalov.challenge.services.ConcreteSource;
import com.raisin.zyurkalov.challenge.services.SinkService;
import com.raisin.zyurkalov.challenge.services.SolutionService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChallengeApplicationConfiguration {

    public static final String URL_A = "http://localhost:7299/source/a";
    public static final String URL_B = "http://localhost:7299/source/b";
    public static final String URL_SINK = "http://localhost:7299/sink/a";


    @Bean
    public CombinedSource getSource() {
        return new CombinedSource(getSourceA(),getSourceB());
    }

    @Bean
    @Qualifier("getChallengeRecordJsonMapper")
    public ConcreteSource getSourceA(){
        var source = new ConcreteSource(URL_A);
        source.setMapper(getChallengeRecordJsonMapper());
        return  source;
    }

    @Bean
    public ConcreteSource getSourceB(){
        var source = new ConcreteSource(URL_B);
        source.setMapper(getChallengeRecordXmlMapper());
        return  source;
    }

    @Bean
    public ExceptionsHolder getExceptionsHolder() {
        return new ExceptionsHolder();
    }

    @Bean
    public ChallengeRecordJsonMapper getChallengeRecordJsonMapper() {
        return new ChallengeRecordJsonMapper();
    }

    @Bean
    public ChallengeRecordXmlMapper getChallengeRecordXmlMapper() {
        return new ChallengeRecordXmlMapper();
    }

    @Bean
    public SolutionService getSolutionService() {
        return new SolutionService();
    }

    @Bean
    public SinkService getSinkService() {
        return new SinkService(URL_SINK);
    }
}
