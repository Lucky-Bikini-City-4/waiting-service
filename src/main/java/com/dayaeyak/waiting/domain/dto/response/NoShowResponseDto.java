package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.entity.NoShow;
import lombok.Builder;

import java.math.BigInteger;

@Builder
public record NoShowResponseDto (
    BigInteger noShowId,
    BigInteger restaurantId,
    BigInteger userId
){

    public static NoShowResponseDto from(NoShow noshow){
        return NoShowResponseDto.builder()
                .noShowId(noshow.getNoShowId())
                .restaurantId(noshow.getRestaurantId())
                .userId(noshow.getUserId())
                .build();
    }
}