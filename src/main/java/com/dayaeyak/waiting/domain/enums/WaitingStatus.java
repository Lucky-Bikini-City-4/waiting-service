package com.dayaeyak.waiting.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {
    OWNER_CANCEL,
    USER_CANCEL,
    USER_NO_SHOW,
    USER_ENTERED,

    WAITING,
    USER_COMING,
    USER_ARRIVED,
    FIRST_CALLED,
    FINAL_CALLED,
}
