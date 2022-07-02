package org.example.vo.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.domains.auth.AuthUser;
import org.example.vo.BaseVO;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentCreateVO  implements BaseVO {

    private String name;
    private String surname;
    private AuthUser user;

}
