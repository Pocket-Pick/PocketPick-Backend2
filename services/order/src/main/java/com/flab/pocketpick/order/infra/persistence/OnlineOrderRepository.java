package com.flab.pocketpick.order.infra.persistence;

import com.flab.pocketpick.order.domain.order.OnlineOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineOrderRepository extends JpaRepository<OnlineOrder, Long> {
}
