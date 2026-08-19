package com.flab.pocketpick.user.application.usecase;

import com.flab.pocketpick.user.application.dto.SignupRequest;

public interface UserUseCase {

    void signup(SignupRequest request);
}
