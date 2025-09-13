package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.entity.NoShow;
import lombok.Builder;
import lombok.Setter;




public record NoShowResponseDto (
    Long noShowId,
    Long restaurantId,
    Long userId
){

    public static NoShowResponseDto from(NoShow noshow){
        return new NoShowResponseDto(
                noshow.getNoShowId(),
                noshow.getRestaurantId(),
                noshow.getUserId()
        );
    }
}