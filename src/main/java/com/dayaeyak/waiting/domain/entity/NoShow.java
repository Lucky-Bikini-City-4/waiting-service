package com.dayaeyak.waiting.domain.entity;

import com.dayaeyak.waiting.common.entity.BaseEntity;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.math.BigInteger;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "no_shows")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE waitings SET deleted_at = now() WHERE waiting_id = ?")
public class NoShow extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "no_show_id")
    private BigInteger noShowId;

    @Column(name ="user_id", nullable = false)
    private BigInteger userId;

    @Column(name ="restaurant_id", nullable = false)
    private BigInteger restaurantId;

    @Builder
    public NoShow(BigInteger restaurantId,
                   BigInteger userId){
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}
