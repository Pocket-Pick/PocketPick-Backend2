package com.flab.pocketpick.user.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank @Email
        String email,

        @NotBlank
        String password,

        @NotBlank
        String nickname,

        String region,

        String introduce
) {
}
