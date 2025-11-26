package com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    private String email;
    private String password;
}
