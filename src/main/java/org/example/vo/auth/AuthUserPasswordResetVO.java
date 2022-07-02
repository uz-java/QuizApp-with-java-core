package org.example.vo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserPasswordResetVO {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
