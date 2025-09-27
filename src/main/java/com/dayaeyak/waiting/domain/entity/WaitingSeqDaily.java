package com.dayaeyak.waiting.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "waiting_seq_dailys")
public class WaitingSeqDaily {

    @EmbeddedId
    private WaitingSeqDailyId id;

    @Column(name ="waiting_seq", nullable = false)
    private Long waitingSeq;


    public void increaseWaitingSeq() {
        this.waitingSeq++;
    }

    @Builder
    public WaitingSeqDaily(WaitingSeqDailyId id,
                           Long waitingSeq){
        this.id = id;
        this.waitingSeq = waitingSeq;
    }
}
