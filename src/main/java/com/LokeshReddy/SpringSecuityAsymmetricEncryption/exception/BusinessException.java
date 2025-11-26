package com.LokeshReddy.SpringSecuityAsymmetricEncryption.exception;

import java.util.Objects;

public class BusinessException extends RuntimeException{
    private final ErrorCode errorCode;
    private final Objects[] args;
    public BusinessException(final ErrorCode errorCode, final Object... args){
        super(getFormattedMessage(errorCode,args));
        this.errorCode=errorCode;
        this.args= (Objects[]) args;
    }
    public static String getFormattedMessage(ErrorCode errorCode,Object...args){
        if(args!=null&&args.length>0){
            return String.format(errorCode.getDefautMessage(),args);
        }
        return String.format(errorCode.getDefautMessage());
    }

}
