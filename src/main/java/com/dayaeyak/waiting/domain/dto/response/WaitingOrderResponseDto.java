package com.dayaeyak.waiting.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingOrderResponseDto {
    private Long waiting_seq;
    private Long waiting_id;
}
