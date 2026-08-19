package com.flab.pocketpick.user.domain.user.policy;

import com.flab.pocketpick.user.domain.user.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class PasswordPolicy {

    private static final int MIN = 8;

    public void validate(String password) {
        if (password == null || password.length() < MIN) {
            throw new InvalidPasswordException();
        }
    }
}
