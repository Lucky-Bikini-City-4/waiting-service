package com.dayaeyak.waiting.domain.kafka.dto;

import com.dayaeyak.waiting.domain.kafka.enums.FromSellerType;
import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;

import java.time.LocalDateTime;

public record CustomerFromSellerDto(
        Long userId,
        ServiceType serviceType,
        Long serviceId,
        FromSellerType type,
        String userName,
        LocalDateTime deadline
) {
}
