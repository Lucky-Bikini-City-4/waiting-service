package com.dayaeyak.waiting.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WaitingStatus {
    OWNER_CANCEL,   // 사장 취소
    CANCEL,   // 유저 취소
    NO_ANSWER2,  // 유저 무응답2 = 노쇼함
    ENTERED,  // 유저 입장됨

    WAITING, // 유저 대기 (호출전)
    COMMING, // 유저 가는중 (호출후)
    ARRIVED, // 유저 도착 (호출후)
    NO_ANSWER,  // 유저 무응답1
    FIRST_CALLED, // 호출 후 응답 대기 (호출후)
    FINAL_CALLED, // 두번째 호출 후 응답 대기()
}
