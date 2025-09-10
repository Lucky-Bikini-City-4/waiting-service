package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class NoShowCreateRequestDto {
    private BigInteger restaurantId;
    private BigInteger userId;
}
