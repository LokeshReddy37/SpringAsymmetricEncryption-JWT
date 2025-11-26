package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProfileUpdateRequestDto {
    private String firstName;
    private String lastName;
    private LocalDateTime dateOFBirth;
}
