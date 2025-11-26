package com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.AuthenticationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RefreshRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RegistrationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.response.AuthenticationResponse;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    void regester(RegistrationRequest request);
    AuthenticationResponse refreshToken(RefreshRequest request);
}
