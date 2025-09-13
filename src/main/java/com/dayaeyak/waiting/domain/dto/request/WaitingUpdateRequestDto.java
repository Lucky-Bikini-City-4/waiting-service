package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class WaitingUpdateRequestDto {
    private BigInteger restaurantId;
    private BigInteger userId;
    private BigInteger waitingId;
}
