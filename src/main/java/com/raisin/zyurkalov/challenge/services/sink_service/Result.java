package com.raisin.zyurkalov.challenge.services.sink_service;

import com.raisin.zyurkalov.challenge.services.Kind;
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
