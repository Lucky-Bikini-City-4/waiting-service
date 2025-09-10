package com.dayaeyak.waiting.domain.dto.response;

import java.util.List;

public record NoShowListResponseDto(
        long count,
        List<NoShowResponseDto> data
) {

}
