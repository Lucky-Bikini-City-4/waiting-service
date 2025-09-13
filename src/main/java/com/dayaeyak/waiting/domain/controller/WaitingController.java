package com.dayaeyak.waiting.domain.controller;

import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.request.WaitingRequestDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingCreateResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingListResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingResponseDto;
import com.dayaeyak.waiting.domain.dto.response.WaitingUpdateResponseDto;
import com.dayaeyak.waiting.domain.service.WaitingActionService;
import com.dayaeyak.waiting.domain.service.WaitingService;
import com.dayaeyak.waiting.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.Long;
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
            @PathVariable Long waitingId){
        WaitingResponseDto responseDto = waitingService.getWaiting(waitingId);
        return ApiResponse.success(200, "웨이팅 단건이 조회되었습니다. ", responseDto);
    }

    // 가게별 웨이팅 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<WaitingListResponseDto>> getWaitings(
            @RequestParam Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        WaitingListResponseDto responseDto = waitingService.getWaitings(restaurantId, page, size);
        return ApiResponse.success(200, "", responseDto);
    }

    // 웨이팅 수정 액션 통합형
    @PostMapping("/{waitingId}/actions/{action}")
    public ResponseEntity<ApiResponse<WaitingUpdateResponseDto>> updateAction(
            @PathVariable Long waitingId,
            @PathVariable String action,
            @RequestBody(required = false) Map<String, Object> payload) {
        WaitingUpdateResponseDto responseDto = waitingActionService.updateWaiting(waitingId, action, payload);

        return ApiResponse.success(202, "웨이팅이 수정되었습니다.", responseDto);
    }


    // 웨이팅 단건 삭제
    @DeleteMapping("/{waitingId}")
    public ResponseEntity<ApiResponse<Void>> deleteWaiting(
            @Validated
            @PathVariable Long waitingId){
        waitingService.deleteWaiting(waitingId);
        return ApiResponse.success(204, "웨이팅이 삭제되었습니다.", null);
    }

    // 웨이팅 모든 음식점에서 전체 삭제
    @DeleteMapping("/all/{restaurantId}")
    public ResponseEntity<ApiResponse<Void>> deleteWaitingAll(
            @Validated
            @PathVariable Long restaurantId){
        waitingService.deleteWaitingAll(restaurantId);
        return ApiResponse.success(204, "웨이팅이 삭제되었습니다.", null);
    }
}
