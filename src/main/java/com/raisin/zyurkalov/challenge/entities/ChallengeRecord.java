package com.raisin.zyurkalov.challenge.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeRecord {
    private String id;
    private String status;
    private String text;

}
