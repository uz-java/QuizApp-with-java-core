package org.example.vo.answer;

import org.example.enums.AnswerStatus;
import org.example.vo.GenericVO;
public class AnswerUpdateVO extends GenericVO {
    private String body;

    private AnswerStatus status;

    public AnswerUpdateVO(Long id, String body, AnswerStatus status) {
        super(id);
        this.body = body;
        this.status = status;
    }
}
