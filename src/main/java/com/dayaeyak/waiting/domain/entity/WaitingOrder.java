package com.dayaeyak.waiting.domain.entity;

import com.dayaeyak.waiting.common.entity.BaseEntity;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.UniqueElements;


@Getter
@Entity
@Setter
@NoArgsConstructor
@Table(name = "waiting_orders")
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE waiting_orders SET deleted_at = now() WHERE waiting_id = ?")
public class WaitingOrder extends BaseEntity {

    @Id
    @Column(name ="waiting_seq", nullable = false)  @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long waitingSeq;

    @Column(name = "waiting_id", unique = true, nullable = false)
    private Long waitingId;

    @Column(name ="restaurant_id", nullable = false)
    private Long restaurantId;

    @Enumerated(EnumType.STRING)
    @Column(name ="waiting_status", nullable = false, length = 32)
    private WaitingStatus waitingStatus;

    @Column(name ="deadline")
    private String deadline;

    @Builder
    public WaitingOrder(
            Long waitingSeq,
            Long waitingId,
            Long restaurantId,
            WaitingStatus waitingStatus
    ) {
        this.waitingSeq = waitingSeq;
        this.waitingId = waitingId;
        this.restaurantId = restaurantId;
        this.waitingStatus = waitingStatus;
        this.deadline = null;
    }
}