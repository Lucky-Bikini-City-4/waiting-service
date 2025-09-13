package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WaitingOrderRepository extends JpaRepository<WaitingOrder, Long> {
    WaitingOrder findDistinctFirstByWaitingId(Long waitingId);
}

//public interface WaitingOrderRepository extends CrudRepository<WaitingOrder, Long> {
//
//
//}


