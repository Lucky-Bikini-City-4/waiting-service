package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingListResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingOrderResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingOrderRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingService {


    private final WaitingOrderRepository waitingOrderRepository;
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



        Long savedWaitingId = savedWaiting.getWaitingId();
        // 추후에 레디스에 올려야함
        Time initialTime = Time.valueOf(LocalTime.now());

        WaitingOrder waitingOrder = WaitingOrder.builder()
                .waitingId(waiting.getWaitingId())
                .restaurantId(waiting.getRestaurantId())
                .waitingStatus(waiting.getWaitingStatus())
                .initialTime(initialTime.toString())
                .build();

        waitingOrderRepository.save(waitingOrder);

        // 사장에게 등록되었다는 알람 보내야함

        return new WaitingCreateResponseDto(savedWaitingId);
    }

    public WaitingResponseDto getWaiting(Long waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new EntityNotFoundException());
        return WaitingResponseDto.from(waiting);
    }

    public WaitingListResponseDto getWaitings(
            Long restaurantId,
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

    public void deleteWaiting(Long waitingId){
        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new EntityNotFoundException());

        if (waiting.getDeletedAt() != null) return;
        waiting.delete();
    }

    public void deleteWaitingAll(Long restaurantId){
        List<Waiting> waitingList = waitingRepository.findByRestaurantIdAndDeletedAtIsNull(restaurantId);
        if (waitingList.isEmpty()) return;

        for (Waiting waiting : waitingList){
            if (waiting.getDeletedAt()==null) {
                waiting.delete();
            }
        }
    }

    public WaitingOrderResponseDto getWaitingOrder(Long waitingId){
        WaitingOrder wo = waitingOrderRepository.findByWaitingIdAndDeletedAtIsNull(waitingId);
        if(wo == null) {
            throw new EntityNotFoundException();
        }
        long cnt = waitingOrderRepository.countByWaitingSeqLessThanAndWaitingStatusNotIn(
                wo.getWaitingSeq(),
                List.of(WaitingStatus.OWNER_CANCEL, WaitingStatus.USER_CANCEL,
                        WaitingStatus.USER_ENTERED, WaitingStatus.USER_NO_SHOW)
        );
        return new WaitingOrderResponseDto(cnt, waitingId);
    }
}
