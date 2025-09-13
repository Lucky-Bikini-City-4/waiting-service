package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.entity.NoShow;
import lombok.Builder;



@Builder
public record NoShowResponseDto (
    Long noShowId,
    Long restaurantId,
    Long userId
){

    public static NoShowResponseDto from(NoShow noshow){
        return NoShowResponseDto.builder()
                .noShowId(noshow.getNoShowId())
                .restaurantId(noshow.getRestaurantId())
                .userId(noshow.getUserId())
                .build();
    }
}