package org.example.vo.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domains.auth.AuthUser;
import org.example.domains.subject.SubjectEntity;
import org.example.vo.BaseVO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeacherCreateVO implements BaseVO {
    private String name;
    private String surname;
    private AuthUser user;
    private List<SubjectEntity> subjectList;
}
