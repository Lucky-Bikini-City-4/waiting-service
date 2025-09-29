package com.dayaeyak.waiting.domain.kafka.dto;

import com.dayaeyak.waiting.domain.kafka.enums.CustomerWaitingType;
import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;

import java.time.LocalDateTime;

public record CustomerWaitingDto(
    Long userId,
    ServiceType serviceType,
    Long serviceId,
    CustomerWaitingType customerWaitingType,
    String userName,
    Long people,
    LocalDateTime date,
    Long waiting //내 앞에 몇명?
) {}