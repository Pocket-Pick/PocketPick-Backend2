package com.flab.pocketpick.user.infra.persistence;

import com.flab.pocketpick.user.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);
}
