package com.dayaeyak.waiting.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {
    WAITING, CALLED, ENTERED, USER_COMING, USER_NOSHOW, OWNER_CALLED, USER_CALLED;
}
