package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;


public interface WaitingOrderRepository extends JpaRepository<WaitingOrder, Long> {
    WaitingOrder findDistinctFirstByWaitingId(Long waitingId);
    WaitingOrder findByWaitingIdAndDeletedAtIsNull(Long waitingId);
    Long countByWaitingSeqLessThanAndWaitingStatusNotIn(
            Long waitingSeq,
            Collection<WaitingStatus> excludedStatuses
    );

}

//public interface WaitingOrderRepository extends CrudRepository<WaitingOrder, Long> {
//
// TODO
//}


