package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.Waiting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.Long;
import java.util.List;

@Repository
public interface WaitingRepository extends JpaRepository<Waiting, Long> {
    Waiting save(Waiting waiting);
    Page<Waiting> findByRestaurantIdAndDeletedAtIsNull(Long restaurantId, Pageable pageable);
    List<Waiting> findByRestaurantIdAndDeletedAtIsNull(Long restaurantId);

}

