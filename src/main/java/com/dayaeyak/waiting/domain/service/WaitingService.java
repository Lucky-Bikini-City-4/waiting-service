package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingListResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.math.BigInteger;
import java.util.List;

@Service
@Transactional
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

    public WaitingListResponseDto getWaitings(
            BigInteger restaurantId,
            int page,
            int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Waiting> result = waitingRepository.findByRestaurantIdAndDeletedAtIsNull(restaurantId, pageable);
        List<WaitingResponseDto> data = result.getContent().stream()
                .map(WaitingResponseDto::from)
                .toList();

        return new WaitingListResponseDto(result.getTotalElements(), data);
    }

    public void deleteWaiting(BigInteger waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new EntityNotFoundException());

        if (waiting.getDeletedAt() != null) return;
        waiting.delete();
    }

    public void deleteWaitingAll(BigInteger restaurantId){
        List<Waiting> waitingList = waitingRepository.findByRestaurantIdAndDeletedAtIsNull(restaurantId);
        if (waitingList.isEmpty()) return;

        for (Waiting waiting : waitingList){
            if (waiting.getDeletedAt()==null) {
                waiting.delete();
            }
        }
    }
}
