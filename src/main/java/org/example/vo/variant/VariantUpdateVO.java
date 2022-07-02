package org.example.vo.variant;

import lombok.Builder;
import org.example.domains.auth.AuthUser;
import org.example.enums.QuestionStatus;
import org.example.vo.GenericVO;
import org.example.vo.question.QuestionVO;

import java.util.List;

public class VariantUpdateVO extends GenericVO {
    private AuthUser user;
    private QuestionStatus status;
    private List<QuestionVO> questions;
    private Integer numberOfRightAnswers;

    @Builder(builderMethodName = "childBuilder")
    public VariantUpdateVO(Long id, AuthUser user, QuestionStatus status, List<QuestionVO> questions, Integer numberOfRightAnswers) {
        super(id);
        this.user = user;
        this.status = status;
        this.questions = questions;
        this.numberOfRightAnswers = numberOfRightAnswers;
    }
}
