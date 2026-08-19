package com.flab.pocketpick.user.domain.user.exception;

import com.flab.pocketpick.user.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidNicknameException extends BaseException {

    public InvalidNicknameException() {
        super("닉네임은 2자 이상 20자 이하여야 합니다.", HttpStatus.BAD_REQUEST);
    }
}
