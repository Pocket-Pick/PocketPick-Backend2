package com.flab.pocketpick.order.infra.persistence;

import com.flab.pocketpick.order.domain.order.entity.InboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxEventRepository extends JpaRepository<InboxEvent, Long> {
    boolean existsByInboxKey(String inboxKey);
}
