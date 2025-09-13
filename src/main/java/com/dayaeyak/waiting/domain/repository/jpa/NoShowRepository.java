package com.dayaeyak.waiting.domain.repository.jpa;

import com.dayaeyak.waiting.domain.entity.NoShow;
import com.dayaeyak.waiting.domain.entity.Waiting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NoShowRepository extends JpaRepository<NoShow, BigInteger> {
    NoShow save(NoShow noShow);
    Page<NoShow> findByRestaurantIdAndDeletedAtIsNull(BigInteger restaurantId, Pageable pageable);
    List<NoShow> findByRestaurantIdAndDeletedAtIsNull(BigInteger restaurantId);

}

