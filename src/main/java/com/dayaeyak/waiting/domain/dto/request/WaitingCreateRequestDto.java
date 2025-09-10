package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class WaitingCreateRequestDto {
    private BigInteger restaurantId;
    private BigInteger datesId;
    private BigInteger userId;
    private Integer userCount;
}
