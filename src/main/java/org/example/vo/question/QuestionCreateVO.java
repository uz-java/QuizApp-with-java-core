package org.example.vo.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.enums.QuestionStatus;
import org.example.vo.BaseVO;
import org.example.vo.answer.AnswerCreateVO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuestionCreateVO implements BaseVO {

    private String body;
    private QuestionStatus status;
    private List<AnswerCreateVO> answers;
    private String subjectName;
    private Long createdBy;
}
