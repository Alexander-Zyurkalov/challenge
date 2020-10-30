package com.raisin.zyurkalov.challenge;

import com.raisin.zyurkalov.challenge.adapters.ExceptionsHolder;
import com.raisin.zyurkalov.challenge.entities.Status;
import com.raisin.zyurkalov.challenge.services.Kind;
import com.raisin.zyurkalov.challenge.services.SinkService;
import com.raisin.zyurkalov.challenge.services.SolutionService;
import com.raisin.zyurkalov.challenge.services.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.Disposable;

@SpringBootApplication
public class ChallengeApplication {

    @Qualifier("getSource")
    @Autowired
    Source source;

    @Autowired
    ExceptionsHolder exceptionsHolder;


    @Autowired
    SolutionService solutionService;


    @Autowired
    SinkService sinkService;

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(ChallengeApplication.class).web(WebApplicationType.NONE).run(args);
    }



    @Bean
    CommandLineRunner run() {
        return (evt) -> {
//            var list = source.getChallengeRecords().collectList().block();
//            exceptionsHolder.getExceptions().subscribe(System.out::println);

            Disposable disposable = source.getChallengeRecords().subscribe(
                    record -> {
                        if (record.getStatus() == Status.OK)
                            solutionService.addRecord(record);
                    }
            );

            while (!disposable.isDisposed() || solutionService.getNumberOfJoined() > 0) {
                solutionService.processJointed().forEach(
                        r -> {
                            sinkService.sendResult(Kind.JOINED, r.getId());
                            System.out.println("Sending Joined: " + r.getId());
                        }
                );
            }
            solutionService.processOrphans().forEach(
                    r -> {
                        System.out.println("Sending Orphaned: " + r.getId());
                        sinkService.sendResult(Kind.ORPHANED, r.getId());
                    }
            );


            exceptionsHolder.awaitAndShutdown();
        };

    }

}
