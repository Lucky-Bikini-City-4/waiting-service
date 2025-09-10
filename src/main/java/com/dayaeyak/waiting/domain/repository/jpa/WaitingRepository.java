package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.Waiting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface WaitingRepository extends JpaRepository<Waiting, BigInteger> {
    Waiting save(Waiting waiting);
    Page<Waiting> findByRestaurantIdAndDeletedAtIsNull(BigInteger restaurantId, Pageable pageable);
    List<Waiting> findByRestaurantIdAndDeletedAtIsNull(BigInteger restaurantId);

}

