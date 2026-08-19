package com.flab.pocketpick.user.domain.user.policy;

import com.flab.pocketpick.user.domain.user.exception.InvalidNicknameException;
import org.springframework.stereotype.Component;

@Component
public class NicknamePolicy {

    private static final int MIN = 2;
    private static final int MAX = 20;

    public void validate(String nickname) {
        if (nickname == null || nickname.length() < MIN || nickname.length() > MAX) {
            throw new InvalidNicknameException();
        }
    }
}
