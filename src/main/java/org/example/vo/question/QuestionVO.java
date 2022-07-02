package org.example.vo.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.QuestionStatus;
import org.example.vo.GenericVO;
import org.example.vo.answer.AnswerVO;
import org.example.vo.subject.SubjectVO;

import java.util.List;

@Setter
@Getter
public class QuestionVO extends GenericVO {

    private String body;
    private QuestionStatus status;
    private List<AnswerVO> answers;
    private SubjectVO subject;

    @Builder(builderMethodName = "childBuilder")
    public QuestionVO(Long id, String body, QuestionStatus status, List<AnswerVO> answers, SubjectVO subject) {
        super(id);
        this.body = body;
        this.status = status;
        this.answers = answers;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "QuestionVO{" +
                "body='" + body + '\'' +
                ", status=" + status +
                ", answers=" + answers +
                ", subject=" + subject +
                ", id=" + id +
                '}';
    }
}
