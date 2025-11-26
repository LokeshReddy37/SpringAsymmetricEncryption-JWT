package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangePasswordRequestDto {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
