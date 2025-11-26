package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ChangePasswordRequestDto;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ProfileUpdateRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateProfileInfo(ProfileUpdateRequestDto updateDto ,String userId);

    void changePassword(ChangePasswordRequestDto changePasswordDto,String userId);

    void deactivateAccount(String userId);

    void reactivateAccount(String userId);

    void deleteAccount(String userId);
}
