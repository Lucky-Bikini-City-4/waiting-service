package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.response.WaitingUpdateResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.CallType;
import com.dayaeyak.waiting.domain.enums.CancelType;
import com.dayaeyak.waiting.domain.enums.Transition;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerCancelDto;
import com.dayaeyak.waiting.domain.kafka.dto.CustomerFromSellerDto;
import com.dayaeyak.waiting.domain.kafka.dto.SellerDto;
import com.dayaeyak.waiting.domain.kafka.enums.FromSellerType;
import com.dayaeyak.waiting.domain.kafka.enums.SellerAlarmType;
import com.dayaeyak.waiting.domain.kafka.enums.ServiceType;
import com.dayaeyak.waiting.domain.kafka.service.AlarmService;
import com.dayaeyak.waiting.domain.repository.cache.redis.WaitingCacheRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingOrderRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingSeqDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.sql.Time;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor

public class WaitingActionService {

    private final WaitingRepository waitingRepository;
    private final WaitingOrderRepository waitingOrderRepository;
    private final WaitingCacheRepository waitingCache;
    private final WaitingSeqDailyRepository waitingSeqDailyRepository;
    private final AlarmService alarmService;


    // TODO 노쇼 처리 - 고도화때 작업 처리반 쪽으로 넘기기
    public WaitingUpdateResponseDto updateWaiting(Long restaurantId, Long waitingId, String action, Map<String, Object> payload){
        return switch(action.toLowerCase()) {
            case "waiting_call" -> call(restaurantId, waitingId, payload);
            case "waiting_user_coming" -> user_coming(restaurantId, waitingId, payload);
            case "waiting_user_arrived" -> user_arrived(restaurantId, waitingId, payload);
            case "waiting_cancel" -> cancel(restaurantId, waitingId, payload);
            case "waiting_entered" -> entered(restaurantId, waitingId, payload);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 Action 입니다.");
        };
    }

    private WaitingUpdateResponseDto call(Long restaurantId, Long waitingId, Map<String, Object> payload){
        CallType callType = requireEnum(payload, "type", CallType.class); // IMMINENT/FIRST/FINAL

//        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
//        if(waitingOrder == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        // Redis
        Map<String, Object> wo = new HashMap<String, Object>();

        switch (callType) {
            // TODO case IMMINENT -> 앞에서 2팀에게 순서임박 자동 호출 (고도화 때 작업 처리반에서 진행 예정, 알람한테 요청)
            case FIRST -> {
//                waitingOrder.setWaitingStatus(WaitingStatus.FIRST_CALLED);

                long deadline = System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(10);
                LocalDateTime deadlineLdt =
                        Instant.ofEpochMilli(deadline).atZone(ZoneId.systemDefault()).toLocalDateTime();
                // Redis
                wo.put("status", WaitingStatus.FIRST_CALLED);
                wo.put("deadlineAt", deadline);

                // 사장 호출1
                CustomerFromSellerDto customerFromSellerDto = new CustomerFromSellerDto(
                        0L, ServiceType.WAITING, waitingId, FromSellerType.FIRST, "이다예", deadlineLdt);

                alarmService.sendMessageQueue2("waiting-seller", "", customerFromSellerDto);


                // 지연 큐에 등록(전역 키 사용)
                // TODO 고도화때 작업 처리반 쪽으로 넘기기
                waitingCache.mapWaitingToRestaurant(waitingId, restaurantId);
                waitingCache.enqueueDeadline(waitingId, Transition.NO_ANSWER.name(), deadline);
                waitingCache.hsetFields(restaurantId, waitingId, wo);
                return new WaitingUpdateResponseDto(waitingId, WaitingStatus.FIRST_CALLED);
            }
            case FINAL -> {
//                waitingOrder.setWaitingStatus(WaitingStatus.FINAL_CALLED);

                long deadline = System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(10);

                // Redis
                wo.put("status", WaitingStatus.FINAL_CALLED);
                wo.put("deadlineAt", deadline);

                LocalDateTime deadlineLdt =
                        Instant.ofEpochMilli(deadline).atZone(ZoneId.systemDefault()).toLocalDateTime();

                // 사장 호출1
                CustomerFromSellerDto customerFromSellerDto = new CustomerFromSellerDto(
                        0L, ServiceType.WAITING, waitingId, FromSellerType.LAST, "이다예", deadlineLdt);

                alarmService.sendMessageQueue2("waiting-seller", "", customerFromSellerDto);


                // TODO 고도화때 작업 처리반 쪽으로 넘기기
                waitingCache.mapWaitingToRestaurant(waitingId, restaurantId);
                waitingCache.enqueueDeadline(waitingId, Transition.NO_ANSWER2.name(), deadline);
                waitingCache.hsetFields(restaurantId, waitingId, wo);
                return new WaitingUpdateResponseDto(waitingId, WaitingStatus.FINAL_CALLED);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 call Type입니다.");
        }

        // TODO 배포전에 외국 시간으로 바꾸기
        // PostgreSQL DB 방식
//        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(10).toString());
//        Waiting waiting = waitingRepository.findByWaitingId(waitingId);

//        waitingOrderRepository.save(waitingOrder);

        // Redis적용
//        waitingCache.hsetFields(restaurantId, waitingId, wo);
//        return new WaitingUpdateResponseDto(waitingId, WaitingStatus.FINAL_CALLED);
    }

    private WaitingUpdateResponseDto user_coming(Long restaurantId, Long waitingId, Map<String, Object> payload){
//        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
//        if(waitingOrder == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        // Redis
        Map<String, Object> wo = new HashMap<String, Object>();

        Object tmp = payload.get("move_time");
        Long moveTime = Long.parseLong(tmp.toString());

//        waitingOrder.setWaitingStatus(WaitingStatus.COMMING);
        long deadline = System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(moveTime);

        // Redis
        wo.put("status", WaitingStatus.COMMING);
        wo.put("deadlineAt", System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(moveTime));

        // TODO 알람: 호출 응답시 시간 입력 필요
        SellerDto sellerDto = new SellerDto(
                0L, ServiceType.WAITING, waitingId, SellerAlarmType.CUSTOMER_REPLIED, "이다예");

        alarmService.sendMessageQueue4("waiting-seller", "", sellerDto);

        // TODO 고도화때 작업 처리반 쪽으로 넘기기
        waitingCache.mapWaitingToRestaurant(waitingId, restaurantId);
        waitingCache.enqueueDeadline(waitingId, Transition.NO_ANSWER.name(), deadline);

        // TODO 배포전에 외국 시간으로 바꾸기
//        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(moveTime).toString());
//        waitingOrderRepository.save(waitingOrder);
        // Redis
        waitingCache.hsetFields(restaurantId, waitingId, wo);

        Map<String, String> after = waitingCache.getWaitingDetail(restaurantId, waitingId);

        return new WaitingUpdateResponseDto(waitingId, WaitingStatus.COMMING);
    }

    private WaitingUpdateResponseDto user_arrived(Long restaurantId, Long waitingId, Map<String, Object> payload){
//        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
//        if(waitingOrder == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        // Redis
        Map<String, Object> wo = new HashMap<String, Object>();

//        waitingOrder.setWaitingStatus(WaitingStatus.ARRIVED);

        // Redis
        wo.put("status", WaitingStatus.ARRIVED);
        wo.put("deadlineAt",0L);

        // TODO 알람: 손님 도착
        SellerDto sellerDto = new SellerDto(
                0L, ServiceType.WAITING, waitingId, SellerAlarmType.CUSTOMER_ARRIVED, "이다예");

        alarmService.sendMessageQueue4("waiting-seller", "", sellerDto);

        // TODO 고도화때 작업 처리반 쪽으로 넘기기

        // TODO 배포전에 외국 시간으로 바꾸기
//        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());
//        waitingOrderRepository.save(waitingOrder);

        // Redis
        waitingCache.hsetFields(restaurantId, waitingId, wo);

        return new WaitingUpdateResponseDto(waitingId, WaitingStatus.ARRIVED);
    }

    private WaitingUpdateResponseDto cancel(Long restaurantId, Long waitingId, Map<String, Object> payload){
        CancelType cancelType = requireEnum(payload, "type", CancelType.class); // OWNER, USER

//        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
//        if(waitingOrder == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Redis
        Map<String, Object> wo = new HashMap<String, Object>();

        switch (cancelType) {
            case OWNER -> {
//                waitingOrder.setWaitingStatus(WaitingStatus.OWNER_CANCEL);
                waiting.setWaitingStatus(WaitingStatus.OWNER_CANCEL);
                waiting.setClosedTime(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());

                // TODO 알람: 사장 취소
                CustomerFromSellerCancelDto customerFromSellerCancelDto = new CustomerFromSellerCancelDto(
                        0L, ServiceType.WAITING, waitingId);

                alarmService.sendMessageQueue1("waiting-seller", "", customerFromSellerCancelDto);


                // TODO 고도화때 작업 처리반 쪽으로 넘기기

                waitingCache.deactivateImmediate(restaurantId, waitingId);
            }
            case USER -> {
//                waitingOrder.setWaitingStatus(WaitingStatus.CANCEL);
                waiting.setWaitingStatus(WaitingStatus.CANCEL);
                waiting.setClosedTime(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());

                // TODO 알람: 손님 취소
                SellerDto sellerDto = new SellerDto(
                        0L, ServiceType.WAITING, waitingId, SellerAlarmType.WAITING_CANCELED, "이다예");
                alarmService.sendMessageQueue4("waiting-seller", "", sellerDto);

                // TODO 고도화때 작업 처리반 쪽으로 넘기기

                waitingCache.deactivateImmediate(restaurantId, waitingId);
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 call Type입니다.");
        }

        // TODO 배포전에 외국 시간으로 바꾸기

//        waitingOrderRepository.save(waitingOrder);
        waitingRepository.save(waiting);
        waitingCache.unmapWaiting(waitingId);

        return new WaitingUpdateResponseDto(waitingId, waiting.getWaitingStatus());
    }

    private WaitingUpdateResponseDto entered(Long restaurantId, Long waitingId, Map<String, Object> payload){
//        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
//        if(waitingOrder == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        waiting.setWaitingStatus(WaitingStatus.ENTERED);
//        waitingOrder.setWaitingStatus(WaitingStatus.ENTERED);

        waiting.setClosedTime(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());
        waitingCache.deactivateImmediate(restaurantId, waitingId);

        // TODO 고도화때 작업 처리반 쪽으로 넘기기
//        waitingOrderRepository.save(waitingOrder);
        waitingCache.unmapWaiting(waitingId);
        return new WaitingUpdateResponseDto(waitingId, WaitingStatus.ENTERED);
    }

    private static <E extends Enum<E>> E requireEnum(Map<String,Object> m, String key, Class<E> type){
        if (m == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 바디가 필요합니다. ");
        Object v = m.get(key);
        if (v == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "필수값 누락: "+key);
        try { return Enum.valueOf(type, v.toString().trim().toUpperCase()); }
        catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "허용되지 않는 값: " + key + "=" + v);
        }
    }
}
