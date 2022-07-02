package org.example.vo.variant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.QuestionStatus;
import org.example.vo.BaseVO;

@Builder
@Getter
@Setter
public class VariantCreateVO implements BaseVO {
    private String subjectName;
    private QuestionStatus level;
    private Integer numberOfQuestions;
    private Long userId;
}
