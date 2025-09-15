package com.dayaeyak.waiting.domain.dto.request;

import lombok.Getter;


@Getter
public class WaitingUpdateRequestDto {
    private Long restaurantId;
    private Long userId;
    private Long waitingId;
}
