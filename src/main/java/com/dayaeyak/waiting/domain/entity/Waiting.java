package com.dayaeyak.waiting.domain.entity;

import com.dayaeyak.waiting.common.entity.BaseEntity;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.lang.Long;
import java.sql.Time;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "waitings")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE waitings SET deleted_at = now() WHERE waiting_id = ?")
public class Waiting extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "waiting_id")
    private Long waitingId;

    @Column(name ="restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name ="dates_id", nullable = false)
    private Long datesId;

    @Column(name ="user_id", nullable = false)
    private Long userId;

    @Column(name ="user_count", nullable = false)
    private Integer userCount;

    @Enumerated(EnumType.STRING)
    @Column(name ="waiting_status", nullable = false, length = 32)
    private WaitingStatus waitingStatus;

    @Column(name ="entry_time")
    private Time entryTime;

    @Builder
    public Waiting(Long restaurantId,
                   Long datesId,
                   Long userId,
                   Integer userCount,
                   WaitingStatus waitingStatus){
        this.restaurantId = restaurantId;
        this.datesId = datesId;
        this.userId = userId;
        this.userCount = userCount;
        this.waitingStatus = waitingStatus;
    }
}
