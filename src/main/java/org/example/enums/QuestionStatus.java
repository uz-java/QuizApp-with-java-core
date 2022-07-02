package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter

public enum QuestionStatus {

    EASY(10),
    MEDIUM(50),
    HARD(90);

    private Integer priority;
}
