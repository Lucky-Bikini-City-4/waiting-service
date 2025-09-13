package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;

import java.lang.Long;

@Getter
public class NoShowCreateRequestDto {
    private Long restaurantId;
    private Long userId;
}
