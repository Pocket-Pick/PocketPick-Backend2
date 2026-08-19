package com.flab.pocketpick.user.domain.user.exception;

import com.flab.pocketpick.user.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class NicknameDuplicateException extends BaseException {

    public NicknameDuplicateException() {
        super("이미 사용 중인 닉네임입니다.", HttpStatus.CONFLICT);
    }
}
