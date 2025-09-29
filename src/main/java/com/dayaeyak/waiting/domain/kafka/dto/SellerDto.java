package com.dayaeyak.waiting.domain.kafka.dto;

import com.dayaeyak.waiting.domain.kafka.enums.SellerAlarmType;
import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;

public record SellerDto(
        Long userId,
        ServiceType serviceType,
        Long serviceId,
        SellerAlarmType type,
        String customerName
) {
}


