package com.dayaeyak.waiting.domain.enums;

public enum Transition {
    NO_ANSWER,    // FIRST_CALLED 후 10분 미응답, // 사용자 '이동 중' 응답 후 도착 타임아웃
    NO_ANSWER2,    // FINAL_CALLED 후 10분 미응답
}
