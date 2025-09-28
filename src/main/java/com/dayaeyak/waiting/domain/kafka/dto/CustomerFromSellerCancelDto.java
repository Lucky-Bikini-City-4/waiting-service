package com.dayaeyak.waiting.domain.kafka.dto;

import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;

public record CustomerFromSellerCancelDto(
        Long userId,
        ServiceType serviceType,
        Long serviceId
) {
}