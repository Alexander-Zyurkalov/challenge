package com.raisin.zyurkalov.challenge.entities.sink_service;

import com.raisin.zyurkalov.challenge.entities.Kind;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    Kind kind;
    String id;
}
