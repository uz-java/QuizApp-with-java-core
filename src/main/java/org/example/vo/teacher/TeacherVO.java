package org.example.vo.teacher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.vo.GenericVO;
import org.example.vo.subject.SubjectVO;

import java.util.List;

@Getter
@Setter
@ToString
public class TeacherVO extends GenericVO {

    private String name;
    private String surname;
    private List<SubjectVO> subjectList;

    @Builder
    public TeacherVO(Long id, String name, String surname, List<SubjectVO> subjectList) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.subjectList = subjectList;
    }
}
