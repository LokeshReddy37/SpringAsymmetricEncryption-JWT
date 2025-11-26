package com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND","User Not Found with id %s",HttpStatus.NOT_FOUND),
    USER_NOT_FOUND_BY_EMAIL("USER_NOT_FOUND_BY_EMAIL","User Not Found By Email %s",HttpStatus.NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("NEWPASSOWRD_AND_CONFIRMPASSWORD_NOT_SAME","NewPassword And ConfirmPassword Are Not Same" , HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD("INVALID_CURRENT PASSWORD", "Invalid Current Password",HttpStatus.BAD_REQUEST ),
    ACCOUNT_ALREADY_DEACTIVATED("USER_ALREADY_DEACTIVATED","User Already Deactivated" ,HttpStatus.BAD_REQUEST ),
    EMAIL_ALREADY_EXISTS("EMAIL_ALREADY_EXISTS","Email Already Exists" ,HttpStatus.BAD_REQUEST ),
    Phone_ALREADY_EXISTS("PHONE_ALREADY_EXISTS","Phone Already Exists" ,HttpStatus.BAD_REQUEST ),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH","Password Mismatch" ,HttpStatus.BAD_REQUEST );

    private final String code;
    private final String defautMessage;
    private final HttpStatus status;
    ErrorCode(final String code,final String defautMessage,final HttpStatus status){
        this.code=code;
        this.defautMessage=defautMessage;
        this.status=status;
    }
}
