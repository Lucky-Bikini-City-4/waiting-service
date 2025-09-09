package com.dayaeyak.waiting.domain.controller;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.request.WaitingRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingListResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.service.WaitingActionService;
import com.dayaeyak.waiting.domain.service.WaitingService;
import com.dayaeyak.waiting.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/waitings")
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;
    private final WaitingActionService waitingActionService;

    // 웨이팅 등록
    @PostMapping
    public ResponseEntity<ApiResponse<WaitingCreateResponseDto>> createWaiting(
            @Validated
            @RequestBody WaitingCreateRequestDto requestDto){
        WaitingCreateResponseDto responseDto = waitingService.register(requestDto);
        return ApiResponse.success(201, "웨이팅이 등록되었습니다.", responseDto);
    }

    // 웨이팅 단건 조회
    @GetMapping("/{waitingId}")
    public ResponseEntity<ApiResponse<WaitingResponseDto>> getWaiting(
            @Validated
            @PathVariable Long waitingId,
            @RequestBody WaitingRequestDto requestDto){
        WaitingResponseDto responseDto = waitingService.getWaiting(requestDto);
        return ApiResponse.success(200, "", responseDto);
    }

    // 가게별 웨이팅 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<WaitingListResponseDto>> getWaitings(
            @RequestParam Long restaurantId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        WaitingListResponseDto responseDto = waitingService.getWaitings(restaurantId, status, page, size);
        return ApiResponse.success(200, "", responseDto);
    }


    // 웨이팅 수정 액션 통합형 (check-in, delay, going, arrived, cancel, call, no-show ...)
    @PostMapping("/{waitingId}/actions/{action}")
    public ResponseEntity<ApiResponse<WaitingResponseDto>> performAction(
            @PathVariable Long waitingId,
            @PathVariable String action,
            @RequestHeader(value = "Idempotency-Key", required = false) String idemKey,
            @RequestBody(required = false) Map<String, Object> payload) {

        WaitingResponseDto responseDto = waitingActionService.perform(waitingId, action, idemKey, payload);
        // call 같은 비동기 액션은 202로도 가능
        return ApiResponse.success(202, "웨이팅이 수정되었습니다.", responseDto);
    }

    // 웨이팅 단건 삭제
    @DeleteMapping("/{waitingId}")
    public ResponseEntity<ApiResponse<Void>> deleteWaiting(
            @Validated
            @PathVariable Long waitingId,
            @RequestBody WaitingRequestDto requestDto){
        waitingService.deleteWaiting(requestDto);
        return ApiResponse.success(204, "웨이팅이 삭되었습니다.", null);
    }

    // 웨이팅 모든 음식점에서 전체 삭제
    @DeleteMapping("/all")
    public ResponseEntity<ApiResponse<Void>> deleteWaitingAll(
            @Validated
            @RequestBody WaitingRequestDto requestDto){
        waitingService.deleteWaitingAll(requestDto);
        return ApiResponse.success(204, "웨이팅이 삭되었습니다.", null);
    }
}
