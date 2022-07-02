package org.example.vo.answer;

import lombok.*;
import org.example.enums.AnswerStatus;
import org.example.vo.BaseVO;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreateVO implements BaseVO {

    private String body;

    private AnswerStatus status;
}
