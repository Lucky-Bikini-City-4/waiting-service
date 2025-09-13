package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import lombok.Builder;

import java.sql.Time;

@Builder
public record WaitingResponseDto(
        Long waitingId,
        Long restaurantId,
        Long datesId,
        Long userId,
        Integer userCount,
        WaitingStatus waitingStatus,
        Time entry_time
        ) {

    public static WaitingResponseDto from(Waiting waiting){
        return WaitingResponseDto.builder()
        .waitingId(waiting.getWaitingId())
        .restaurantId(waiting.getRestaurantId())
        .datesId(waiting.getDatesId())
        .userId(waiting.getUserId())
        .userCount(waiting.getUserCount())
        .waitingStatus(waiting.getWaitingStatus())
        .entry_time(waiting.getEntryTime())
        .build();
        }


}
