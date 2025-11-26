package com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.AuthenticationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RefreshRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RegistrationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody final AuthenticationRequest request){
        return ResponseEntity.ok(this.authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody final RegistrationRequest request){
        this.authenticationService.regester(request);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@Valid @RequestBody final RefreshRequest request){
        return ResponseEntity.ok(this.authenticationService.refreshToken(request));
    }

}

