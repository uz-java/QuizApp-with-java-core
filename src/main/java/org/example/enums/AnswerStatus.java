package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AnswerStatus {

    RIGHT(1),
    WRONG(0);


    private final int priority;

}
