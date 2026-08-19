package com.flab.pocketpick.user.application.usecase;

import com.flab.pocketpick.user.application.dto.SignupRequest;
import com.flab.pocketpick.user.domain.user.entity.User;
import com.flab.pocketpick.user.infra.persistence.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPersistenceService {

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void save(SignupRequest request) {
        User user = User.builder()
                .nickname(request.nickname())
                .region(request.region())
                .introduce(request.introduce())
                .build();
        userJpaRepository.save(user);
    }
}
