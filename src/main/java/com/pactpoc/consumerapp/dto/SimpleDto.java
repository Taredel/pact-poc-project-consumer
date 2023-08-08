package com.pactpoc.consumerapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleDto {

    private int simpleInteger;
    private MessageDto warningMessage;

}
