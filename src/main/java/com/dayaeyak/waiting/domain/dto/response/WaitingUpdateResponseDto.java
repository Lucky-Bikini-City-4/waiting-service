package com.dayaeyak.waiting.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
public class WaitingUpdateResponseDto {
    BigInteger waitingId;
}
