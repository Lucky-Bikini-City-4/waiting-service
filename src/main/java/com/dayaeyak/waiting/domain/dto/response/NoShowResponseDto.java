package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.entity.NoShow;
import lombok.Builder;
import lombok.Setter;

import java.math.BigInteger;


public record NoShowResponseDto (
    BigInteger noShowId,
    BigInteger restaurantId,
    BigInteger userId
){

    public static NoShowResponseDto from(NoShow noshow){
        return new NoShowResponseDto(
                noshow.getNoShowId(),
                noshow.getRestaurantId(),
                noshow.getUserId()
        );
    }
}