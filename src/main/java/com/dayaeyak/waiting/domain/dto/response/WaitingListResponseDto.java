package com.dayaeyak.waiting.domain.dto.response;

import java.util.List;

public record WaitingListResponseDto(
        long count,
        List<WaitingResponseDto> data
) {


}
