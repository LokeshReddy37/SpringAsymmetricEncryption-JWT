package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.impl;


import com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception.BusinessException;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception.ErrorCode;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.User;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserMapper;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserRepository;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserService;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ChangePasswordRequestDto;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ProfileUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;//to encrypt password and it is implemented in config package
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String userEmail)  {
        return this.userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND_BY_EMAIL,userEmail));
    }

    @Override
    public void updateProfileInfo(ProfileUpdateRequestDto updateDto, String userId) {
       User savedUser=this.userRepository.findById(userId).orElseThrow(
               ()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
       this.userMapper.mergeUserInfo(savedUser,updateDto);
       this.userRepository.save(savedUser);
    }

    @Override
    public void changePassword(ChangePasswordRequestDto changePasswordDto, String userId) {
        if(!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())){
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH, HttpStatus.BAD_REQUEST);
        }
        User savedUser=this.userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));

        if(!savedUser.getPassword().equals(changePasswordDto.getCurrentPassword())){
            throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
        }
        final String password=this.passwordEncoder.encode(changePasswordDto.getNewPassword());
        savedUser.setPassword(password);
        this.userRepository.save(savedUser);

    }

    @Override
    public void deactivateAccount(String userId) {
        User savedUser=this.userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        if(!savedUser.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        savedUser.setEnabled(false);
        this.userRepository.save(savedUser);
    }

    @Override
    public void reactivateAccount(String userId) {
        User savedUser=this.userRepository.findById(userId).orElseThrow(()->new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        if(savedUser.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATED);
        }
        savedUser.setEnabled(true);
        this.userRepository.save(savedUser);
    }

    @Override
    public void deleteAccount(String userId) {
        //this is implemented after all the entities, anything which is associated with user profile the profile must be deleted there

    }


}
