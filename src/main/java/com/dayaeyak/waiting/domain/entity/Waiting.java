package com.dayaeyak.waiting.domain.entity;

import com.dayaeyak.waiting.common.entity.BaseEntity;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Time;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "waitings")
public class Waiting extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "waiting_id")
    private BigInteger waitingId;

    @Column(name ="restaurant_id", nullable = false)
    private BigInteger restaurantId;

    @Column(name ="dates_id", nullable = false)
    private BigInteger datesId;

    @Column(name ="user_id", nullable = false)
    private BigInteger userId;

    @Column(name ="user_count", nullable = false)
    private Integer userCount;

    @Enumerated(EnumType.STRING)
    @Column(name ="waiting_status", nullable = false, length = 32)
    private WaitingStatus waitingStatus;

    @Column(name ="entry_time")
    private Time entryTime;

    @Builder
    public Waiting(BigInteger restaurantId,
                   BigInteger datesId,
                   BigInteger userId,
                   Integer userCount,
                   WaitingStatus waitingStatus){
        this.restaurantId = restaurantId;
        this.datesId = datesId;
        this.userId = userId;
        this.userCount = userCount;
        this.waitingStatus = waitingStatus;
    }
}
