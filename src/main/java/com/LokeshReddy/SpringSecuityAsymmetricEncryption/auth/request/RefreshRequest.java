package com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {
    private String refreshToken;
}
