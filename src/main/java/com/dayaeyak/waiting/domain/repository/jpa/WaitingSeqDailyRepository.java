package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.entity.WaitingSeqDaily;
import com.dayaeyak.waiting.domain.entity.WaitingSeqDailyId;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WaitingSeqDailyRepository extends JpaRepository<WaitingSeqDaily, WaitingSeqDailyId> {
    WaitingSeqDaily save(WaitingSeqDaily daily);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from WaitingSeqDaily w where w.id = :id")
    Optional<WaitingSeqDaily> findByIdForUpdate(@Param("id") WaitingSeqDailyId id);

}

