package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;



import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;

    public WaitingCreateResponseDto register(WaitingCreateRequestDto requestDto){
        Waiting waiting = Waiting.builder()
                .restaurantId(requestDto.getRestaurantId())
                .datesId(requestDto.getDatesId())
                .userId(requestDto.getUserId())
                .userCount(requestDto.getUserCount())
                .waitingStatus(WaitingStatus.WAITING)
                .build();
        Waiting savedWaiting = waitingRepository.save(waiting);

        return new WaitingCreateResponseDto(savedWaiting.getWaitingId());
    }

    public WaitingResponseDto getWaiting(BigInteger waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new EntityNotFoundException());
        return WaitingResponseDto.from(waiting);
    }
}
