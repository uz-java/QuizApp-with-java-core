package org.example.vo.auth;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.AuthRole;

import java.util.Objects;

public class Session {
    public static SessionUser sessionUser;


    public static void setSessionUser(AuthUserVO authUserVO) {
        if (Objects.isNull(authUserVO))
            sessionUser = null;
        else sessionUser = new SessionUser(authUserVO);
    }

    @Getter
    @Setter
    public static class SessionUser {

        private Long id;
        private String username;

        public AuthRole role;

        public SessionUser(AuthUserVO session) {

            this.id = session.id;
            this.username = session.getUsername();
            this.role = session.getRole();
        }

    }
}
