package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user;

import ch.qos.logback.core.util.StringUtil;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.auth.request.RegistrationRequest;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ProfileUpdateRequestDto;
import org.apache.commons.lang3.StringUtils;

public class UserMapper {

    public void mergeUserInfo(User savedUser, ProfileUpdateRequestDto updateDto) {
        if(StringUtils.isNotBlank(updateDto.getFirstName())&&!savedUser.getFirstName().equals(updateDto.getFirstName())){
            savedUser.setFirstName(updateDto.getFirstName());
        }
        if(StringUtils.isNotBlank(updateDto.getLastName())&&!savedUser.getLastName().equals(updateDto.getLastName())){
            savedUser.setLastName(updateDto.getLastName());
        }
        if(updateDto.getDateOFBirth()!=null&&!savedUser.getDateOFBirth().equals(updateDto.getDateOFBirth())){
            savedUser.setDateOFBirth(updateDto.getDateOFBirth().toLocalDate());
        }

    }

    public User toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(request.getPassword())
                .enabled(true)
                .locked(false)
                .expired(false)
                .isEmailVerified(false)
                .isPhoneNumberVerified(false)
                .build();
    }
}



