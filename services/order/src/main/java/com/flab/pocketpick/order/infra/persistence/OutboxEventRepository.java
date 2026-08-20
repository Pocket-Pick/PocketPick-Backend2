package com.flab.pocketpick.order.infra.persistence;

import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByStatusIn(List<OutboxStatus> statuses);
}
