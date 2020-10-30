package com.raisin.zyurkalov.challenge.services.sink_service;

import com.raisin.zyurkalov.challenge.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Status status;
}
