package com.flab.pocketpick.post.domain.post.exception;

import com.flab.pocketpick.post.global.exception.BaseException;
import org.springframework.http.HttpStatus;

public class PostNotFoundException extends BaseException {

    public PostNotFoundException() {
        super("게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
    }
}
