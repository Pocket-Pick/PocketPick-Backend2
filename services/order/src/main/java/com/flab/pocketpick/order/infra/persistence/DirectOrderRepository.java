package com.flab.pocketpick.order.infra.persistence;

import com.flab.pocketpick.order.domain.order.DirectOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectOrderRepository extends JpaRepository<DirectOrder, Long> {
}
