package org.example.vo.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.enums.AuthRole;
import org.example.enums.Status;
import org.example.vo.GenericVO;

@Getter
@Setter
@ToString
public class AuthUserUpdateVO extends GenericVO {
    private String username;
    private String email;
    private AuthRole role;
    private Status status;

    @Builder(builderMethodName = "childBuilder")
    public AuthUserUpdateVO(Long id, String username, String email, AuthRole role, Status status) {
        super(id);
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
    }
}
