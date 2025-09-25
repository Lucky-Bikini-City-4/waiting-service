package com.dayaeyak.waiting.domain.dto.response;

import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class WaitingUpdateResponseDto {
    Long waitingId;
    WaitingStatus status;
}
