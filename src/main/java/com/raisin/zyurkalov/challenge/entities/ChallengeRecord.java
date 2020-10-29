package com.raisin.zyurkalov.challenge.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.Mapping;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeRecord {
    private String id;
    private Status status;
}
