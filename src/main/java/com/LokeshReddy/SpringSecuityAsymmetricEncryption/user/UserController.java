package com.LokeshReddy.SpringSecuityAsymmetricEncryption.user;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ChangePasswordRequestDto;
import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.request.ProfileUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PatchMapping("/me")
    public void updateInformation(@RequestBody @Valid final ProfileUpdateRequestDto requestDto, final Authentication principle){
        this.userService.updateProfileInfo(requestDto,getUserId(principle));
    }

    @PostMapping("/me/password")
    public void changePassword(@RequestBody @Valid final ChangePasswordRequestDto requestDto,final Authentication principle){
        this.userService.changePassword(requestDto,getUserId(principle));
    }
    @PatchMapping("/me/deactivate")
    public void deactivate(@RequestBody @Valid final Authentication principle){
        this.userService.deactivateAccount(getUserId(principle));
    }
    @PatchMapping("/me/reactivate")
    public void reactivate(@RequestBody @Valid final Authentication principle){
        this.userService.reactivateAccount(getUserId(principle));
    }
    @DeleteMapping("/me")
    public void delete(@RequestBody @Valid final Authentication principle){
        this.userService.deleteAccount(getUserId(principle));
    }

    private String getUserId(Authentication principle) {
        return ((User) principle.getPrincipal()).getId();
    }


}
