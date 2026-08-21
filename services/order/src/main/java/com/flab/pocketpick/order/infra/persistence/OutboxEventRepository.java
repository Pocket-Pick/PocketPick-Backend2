package com.flab.pocketpick.order.infra.persistence;

import com.flab.pocketpick.order.domain.order.entity.OutboxEvent;
import com.flab.pocketpick.order.domain.order.enums.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    @Query("""
            SELECT COALESCE(MAX(e.sequenceNumber), 0) + 1
            FROM OutboxEvent e
            WHERE e.aggregateId = :aggregateId
            """)
    int nextSequenceNumber(@Param("aggregateId") Long aggregateId);

    @Query("""
            SELECT e FROM OutboxEvent e
            WHERE e.status IN :statuses
            AND NOT EXISTS (
                SELECT 1 FROM OutboxEvent b
                WHERE b.aggregateId = e.aggregateId
                AND b.sequenceNumber < e.sequenceNumber
                AND b.status NOT IN :completedStatuses
            )
            """)
    List<OutboxEvent> findPublishableEvents(
            @Param("statuses") List<OutboxStatus> statuses,
            @Param("completedStatuses") List<OutboxStatus> completedStatuses
    );
}
