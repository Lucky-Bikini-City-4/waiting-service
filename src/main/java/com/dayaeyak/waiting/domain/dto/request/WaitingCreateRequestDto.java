package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;

import java.lang.Long;

@Getter
public class WaitingCreateRequestDto {
    private Long restaurantId;
    private Long datesId;
    private Long userId;
    private Integer userCount;
}
