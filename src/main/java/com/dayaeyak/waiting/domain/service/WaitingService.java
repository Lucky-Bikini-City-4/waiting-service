package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingListResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingOrderResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.entity.WaitingSeqDaily;
import com.dayaeyak.waiting.domain.entity.WaitingSeqDailyId;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerWaitingDto;
import com.dayaeyak.waiting.domain.kafka.dto.SellerDto;
import com.dayaeyak.waiting.domain.kafka.enums.CustomerWaitingType;
import com.dayaeyak.waiting.domain.kafka.enums.SellerAlarmType;
import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;
import com.dayaeyak.waiting.domain.kafka.service.AlarmService;
import com.dayaeyak.waiting.domain.repository.cache.redis.WaitingCacheRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingOrderRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingSeqDailyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingService {


    private final WaitingOrderRepository waitingOrderRepository;
    private final WaitingRepository waitingRepository;
    private final WaitingCacheRepository waitingCache;
    private final WaitingSeqDailyRepository waitingSeqDailyRepository;
    private final AlarmService alarmService;

    public WaitingCreateResponseDto register(WaitingCreateRequestDto requestDto){

        WaitingSeqDailyId pk = new WaitingSeqDailyId(requestDto.getRestaurantId(), requestDto.getDatesId());

        WaitingSeqDaily nowWaiting = waitingSeqDailyRepository.findByIdForUpdate(pk)
                .orElseGet(() -> waitingSeqDailyRepository.save(
                        new WaitingSeqDaily(pk, 0L) // 없으면 1부터 시작
                ));

        // 순서 증가시킴 (JPA dirty checking으로 업데이트)
        nowWaiting.increaseWaitingSeq();

        Long issuedSeq =  nowWaiting.getWaitingSeq();

        // 대기 생성
        Waiting waiting = Waiting.builder()
                .restaurantId(requestDto.getRestaurantId())
                .datesId(requestDto.getDatesId())
                .userId(requestDto.getUserId())
                .userCount(requestDto.getUserCount())
                .waitingStatus(WaitingStatus.WAITING)
                .waitingSeq(issuedSeq)
                .build();
        Waiting savedWaiting = waitingRepository.save(waiting);

        Long savedWaitingId = savedWaiting.getWaitingId();

//        postgreSQL DB에 넣는 코드
//        WaitingOrder waitingOrder = WaitingOrder.builder()
//                .waitingId(waiting.getWaitingId())
//                .restaurantId(waiting.getRestaurantId())
//                .waitingStatus(waiting.getWaitingStatus())
//                .build();
//        waitingOrderRepository.save(waitingOrder);
//        WaitingOrder savedWaitingOrder = waitingOrderRepository.findByWaitingIdAndDeletedAtIsNull(savedWaiting.getWaitingId());

        long restaurantId = waiting.getRestaurantId();
        long waitingId = waiting.getWaitingId();
        long seq = waiting.getWaitingSeq();
        String status =  WaitingStatus.WAITING.toString();

        waitingCache.initWaitingDetail(
                restaurantId,
                waiting.getWaitingId(),
                waiting.getWaitingSeq(),
                WaitingStatus.WAITING.toString(),
                0);

        waitingCache.activate(restaurantId, waitingId, seq, status);

        long userId = requestDto.getUserId();
        long userCount = requestDto.getUserCount();


        SellerDto sellerDto = new SellerDto(userId, ServiceType.WAITING, waitingId, SellerAlarmType.NEW_WAITING, "이다예");

        // 사장에게 등록되었다는 알람
        alarmService.sendMessageQueue4("waiting-seller", "", sellerDto);

        LocalDateTime now = LocalDateTime.now();
        CustomerWaitingDto customerWaitingDto = new CustomerWaitingDto(
                userId, ServiceType.WAITING, waitingId, CustomerWaitingType.PUT, "이다예", userCount, now, seq);
//         손님에게 등록되었다는 알람
        alarmService.sendMessageQueue3("waiting-seller", "", customerWaitingDto);

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

    public WaitingOrderResponseDto getWaitingOrder(Long restaurantId, Long waitingId){
        Waiting w = waitingRepository.findByWaitingId(waitingId);
//        long restaurantId = w.getRestaurantId();
//        WaitingOrder wo = waitingOrderRepository.findByWaitingIdAndDeletedAtIsNull(waitingId);
//        if(wo == null) {
//            throw new EntityNotFoundException();
//        }
//

        Map<String, String> result =  waitingCache.getWaitingDetail(restaurantId,  waitingId);
//        long seq = Optional.ofNullable(result.get("seq"))
//                .map(Long::parseLong)
//                .orElseThrow(() -> new IllegalStateException("seq not found in redis"));
        String status = result.get("status");
        long deadlineAt = Long.parseLong(result.getOrDefault("deadlineAt", "0"));

//        PostgreSQL DB를 통하는 방식
//        long cnt = waitingOrderRepository.countByRestaurantIdAndWaitingSeqLessThanAndWaitingStatusNotIn(
//                restaurantId,
//                wo.getWaitingSeq(),
//                List.of(WaitingStatus.OWNER_CANCEL, WaitingStatus.CANCEL,
//                        WaitingStatus.ENTERED, WaitingStatus.NO_ANSWER2)
//        );

        long seq = waitingCache.currentOrder(restaurantId, waitingId);

        return new WaitingOrderResponseDto(seq, waitingId);
    }
}
