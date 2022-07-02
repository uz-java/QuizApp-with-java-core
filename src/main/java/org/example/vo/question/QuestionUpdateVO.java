package org.example.vo.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.QuestionStatus;
import org.example.vo.GenericVO;
import org.example.vo.answer.AnswerVO;
import org.example.vo.subject.SubjectVO;

import java.util.List;

@Getter
@Setter
public class QuestionUpdateVO extends GenericVO {
    private String body;
    private QuestionStatus status;
    private List<AnswerVO> answers;
    private SubjectVO subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionUpdateVO(Long id, String body, QuestionStatus status, List<AnswerVO> answers, SubjectVO subject) {
        super(id);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }
}
