package com.flab.pocketpick.user.application.usecase;

import com.flab.pocketpick.user.application.dto.SignupRequest;
import com.flab.pocketpick.user.application.validator.UserValidator;
import com.flab.pocketpick.user.domain.user.exception.NicknameDuplicateException;
import com.flab.pocketpick.user.infra.grpc.AuthGrpcClient;
import com.flab.pocketpick.user.infra.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {

    private final UserJpaRepository userJpaRepository;
    private final UserValidator userValidator;
    private final AuthGrpcClient authGrpcClient;
    private final UserPersistenceService userPersistenceService;

    @Override
    public void signup(SignupRequest request) {
        userValidator.validate(request);

        if (userJpaRepository.existsByNickname(request.nickname())) {
            throw new NicknameDuplicateException();
        }

        authGrpcClient.createCredential(request.email(), request.password());

        try {
            userPersistenceService.save(request);
        } catch (Exception e) {
            authGrpcClient.deleteCredential(request.email());
            throw e;
        }
    }
}
