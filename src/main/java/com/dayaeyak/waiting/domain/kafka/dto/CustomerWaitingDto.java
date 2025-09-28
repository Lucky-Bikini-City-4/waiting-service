package com.dayaeyak.waiting.domain.kafka.dto;

import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;

import java.time.LocalDateTime;

public record CustomerWaitingDto(
    Long userId,
    ServiceType serviceType,
    Long serviceId,
    String userName,
    Integer people,
    LocalDateTime date,
    Integer waiting //내 앞에 몇명?
) {}