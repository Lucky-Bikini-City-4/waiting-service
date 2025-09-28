package com.dayaeyak.waiting.domain.kafka.enums;

public enum SellerAlarmType {

    CUSTOMER_REPLIED, //호출 응답
    CUSTOMER_ARRIVED, // 손님 도착
    NEW_WAITING,       // 새로운 웨이팅 등록
    WAITING_CANCELED   // 손님의 웨이팅 취소

}
