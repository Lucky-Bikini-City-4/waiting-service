package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.response.WaitingUpdateResponseDto;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.CallType;
import com.dayaeyak.waiting.domain.enums.CancelType;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.entity.WaitingOrder;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingOrderRepository;
import com.dayaeyak.waiting.domain.repository.jpa.WaitingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Map;


@Service
@RequiredArgsConstructor

public class WaitingActionService {

    private final WaitingRepository waitingRepository;
    private final WaitingOrderRepository waitingOrderRepository;

    // TODO 노쇼 처리 - 고도화때 작업 처리반 쪽으로 넘기기
    public WaitingUpdateResponseDto updateWaiting(Long waitingId, String action, Map<String, Object> payload){
        return switch(action.toLowerCase()) {
            case "waiting_call" -> call(waitingId, payload);
            case "waiting_user_coming" -> user_coming(waitingId, payload);
            case "waiting_user_arrived" -> user_arrived(waitingId, payload);
            case "waiting_cancel" -> cancel(waitingId, payload);
            case "waiting_entered" -> entered(waitingId, payload);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 Action 입니다.");
        };
    }

    private WaitingUpdateResponseDto call(Long waitingId, Map<String, Object> payload){
        CallType callType = requireEnum(payload, "type", CallType.class); // IMMINENT/FIRST/FINAL

        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
        if(waitingOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        switch (callType) {
            // TODO case IMMINENT -> 앞에서 2팀에게 순서임박 자동 호출 (고도화 때 작업 처리반에서 진행 예정, 알람한테 요청)
            case FIRST -> {
                waitingOrder.setWaitingStatus(WaitingStatus.FIRST_CALLED);
                // TODO 알람한테 요청
                // TODO 고도화때 작업 처리반 쪽으로 넘기기
            }
            case FINAL -> {
                waitingOrder.setWaitingStatus(WaitingStatus.FINAL_CALLED);
                // TODO 알람한테 요청
                // TODO 고도화때 작업 처리반 쪽으로 넘기기
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 call Type입니다.");
        }

        // TODO 배포전에 외국 시간으로 바꾸기
        waitingOrder.setLastCallAt(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());
        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(10).toString());
        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        waiting.setEntryTime(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());

        waitingOrderRepository.save(waitingOrder);
        return new WaitingUpdateResponseDto(waitingId, waitingOrder.getWaitingStatus());
    }

    private WaitingUpdateResponseDto user_coming(Long waitingId, Map<String, Object> payload){
        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
        if(waitingOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Object tmp = payload.get("move_time");
        Long moveTime = Long.parseLong(tmp.toString());

        waitingOrder.setWaitingStatus(WaitingStatus.COMMITING);

        // TODO 알람한테 요청
        // TODO 고도화때 작업 처리반 쪽으로 넘기기

        // TODO 배포전에 외국 시간으로 바꾸기
        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).plusMinutes(moveTime).toString());

        waitingOrderRepository.save(waitingOrder);

        return new WaitingUpdateResponseDto(waitingId, waitingOrder.getWaitingStatus());
    }

    private WaitingUpdateResponseDto user_arrived(Long waitingId, Map<String, Object> payload){
        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
        if(waitingOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        waitingOrder.setWaitingStatus(WaitingStatus.ARRIVED);

        // TODO 알람한테 요청
        // TODO 고도화때 작업 처리반 쪽으로 넘기기

        // TODO 배포전에 외국 시간으로 바꾸기
        waitingOrder.setDeadline(OffsetDateTime.now(ZoneId.of("Asia/Seoul")).toString());

        waitingOrderRepository.save(waitingOrder);
        return new WaitingUpdateResponseDto(waitingId, waitingOrder.getWaitingStatus());
    }

    private WaitingUpdateResponseDto cancel(Long waitingId, Map<String, Object> payload){
        CancelType cancelType = requireEnum(payload, "type", CancelType.class); // OWNER, USER

        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
        if(waitingOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Waiting waiting = waitingRepository.findById(waitingId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        switch (cancelType) {
            case OWNER -> {
                waitingOrder.setWaitingStatus(WaitingStatus.OWNER_CANCEL);
                waiting.setWaitingStatus(WaitingStatus.OWNER_CANCEL);
                // TODO 알람한테 요청
                // TODO 고도화때 작업 처리반 쪽으로 넘기기
            }
            case USER -> {
                waitingOrder.setWaitingStatus(WaitingStatus.CANCEL);
                waiting.setWaitingStatus(WaitingStatus.CANCEL);
                // TODO 알람한테 요청
                // TODO 고도화때 작업 처리반 쪽으로 넘기기
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 call Type입니다.");
        }

        // TODO 배포전에 외국 시간으로 바꾸기

        // TODO 레디스에서 내리고
        // TODO DB에 기록

        waitingOrderRepository.save(waitingOrder);
        waitingRepository.save(waiting);

        return new WaitingUpdateResponseDto(waitingId, waitingOrder.getWaitingStatus());
    }

    private WaitingUpdateResponseDto entered(Long waitingId, Map<String, Object> payload){
        WaitingOrder waitingOrder = waitingOrderRepository.findDistinctFirstByWaitingId(waitingId);
        if(waitingOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Waiting waiting = waitingRepository.findByWaitingId(waitingId);
        waiting.setWaitingStatus(WaitingStatus.ENTERED);
        waitingOrder.setWaitingStatus(WaitingStatus.ENTERED);

        // TODO 알람한테 요청
        // TODO 고도화때 작업 처리반 쪽으로 넘기기

        waitingOrderRepository.save(waitingOrder);
        return new WaitingUpdateResponseDto(waitingId, waitingOrder.getWaitingStatus());
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
