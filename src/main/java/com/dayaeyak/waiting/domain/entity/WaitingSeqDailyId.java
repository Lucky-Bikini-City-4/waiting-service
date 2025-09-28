package com.dayaeyak.waiting.domain.entity;

import com.dayaeyak.waiting.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

@Embeddable
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingSeqDailyId implements java.io.Serializable {

    @Column(name ="restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name ="dates_id", nullable = false)
    private Long datesId;

    @Builder
    public WaitingSeqDailyId(Long restaurantId,
                             Long datesId){
        this.restaurantId = restaurantId;
        this.datesId = datesId;
    }
}
