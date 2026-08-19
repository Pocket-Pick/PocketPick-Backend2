package com.flab.pocketpick.user.domain.user.exception;

import com.flab.pocketpick.user.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BaseException {

    public InvalidPasswordException() {
        super("비밀번호는 8자 이상이어야 합니다.", HttpStatus.BAD_REQUEST);
    }
}
