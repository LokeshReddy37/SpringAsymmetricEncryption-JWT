package com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.impl;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.Security.JwtService;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.AuthenticationService;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.AuthenticationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RefreshRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RegistrationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.response.AuthenticationResponse;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception.BusinessException;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception.ErrorCode;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.role.Role;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.role.RoleRepository;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.User;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserMapper;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication auth= this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        final User user=(User) auth.getPrincipal();
        final String accessToken=this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken=this.jwtService.generateRefreshToken(user.getUsername());
        final String tokenType="Bearer";
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void regester(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkUserPassword(request.getPassword(),request.getConfirmPassword());

        final Role userRole= (Role) this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(()->new EntityNotFoundException("User Role doesn't exist"));

        final List<Role> roles=new ArrayList<>();
        roles.add(userRole);

        final User user =this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving User :"+user);
        this.userRepository.save(user);
    }
    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken=this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType="Bearer";
        return AuthenticationResponse.builder()
                .tokenType(tokenType)
                .refreshToken(request.getRefreshToken())
                .accessToken(newAccessToken)
                .build();
    }
    private void checkUserEmail(final String email) {
        final boolean check=this.userRepository.existsByEmailIgnoreCase(email);
        if(!check){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }
    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean exists=this.userRepository.existByPhoneNumber(phoneNumber);
        if(exists){
            throw new BusinessException(ErrorCode.Phone_ALREADY_EXISTS);
        }
    }

    private void checkUserPassword(String password, String confirmPassword) {
        if(password!=null||password.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }


}
