package com.flab.pocketpick.user.application.validator;

import com.flab.pocketpick.user.application.dto.SignupRequest;
import com.flab.pocketpick.user.domain.user.policy.NicknamePolicy;
import com.flab.pocketpick.user.domain.user.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final NicknamePolicy nicknamePolicy;
    private final PasswordPolicy passwordPolicy;

    public void validate(SignupRequest request) {
        nicknamePolicy.validate(request.nickname());
        passwordPolicy.validate(request.password());
    }
}
