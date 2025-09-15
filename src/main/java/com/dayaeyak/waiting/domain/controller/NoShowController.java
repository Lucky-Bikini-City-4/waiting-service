package com.dayaeyak.waiting.domain.controller;

import com.dayaeyak.waiting.domain.dto.request.NoShowCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.*;
import com.dayaeyak.waiting.domain.service.NoShowService;
import com.dayaeyak.waiting.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.Long;


@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/noShows")
public class NoShowController {

    private final NoShowService noshowService;

    // 노쇼 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NoShowCreateResponseDto>> createNoShow(
            @Validated
            @RequestBody NoShowCreateRequestDto requestDto){
        NoShowCreateResponseDto responseDto = noshowService.register(requestDto);
        return ApiResponse.success(201, "노쇼유저가 등록되었습니다.", responseDto);
    }


    // 노쇼 단건 조회
    @GetMapping("/{noShowId}")
    public ResponseEntity<ApiResponse<NoShowResponseDto>> getNoShow(
            @Validated
            @PathVariable Long noShowId){
        NoShowResponseDto responseDto = noshowService.getNoShow(noShowId);
        return ApiResponse.success(200, "노쇼유저가 조회되었습니다. ", responseDto);
    }

    // 가게별 웨이팅 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<NoShowListResponseDto>> getNoShows(
            @RequestParam Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        NoShowListResponseDto responseDto = noshowService.getNoShows(restaurantId, page, size);
        return ApiResponse.success(200, "", responseDto);
    }

    // 웨이팅 단건 삭제
    @DeleteMapping("/{noShowId}")
    public ResponseEntity<ApiResponse<Void>> deleteNoShow(
            @Validated
            @PathVariable Long noShowId){
        noshowService.deleteNoShow(noShowId);
        return ApiResponse.success(204, "노쇼유저가 삭되었습니다.", null);
    }

    // 웨이팅 모든 음식점에서 전체 삭제
    @DeleteMapping("/all/{restaurantId}")
    public ResponseEntity<ApiResponse<Void>> deleteNoShowAll(
            @Validated
            @PathVariable Long restaurantId){
        noshowService.deleteNoShowAll(restaurantId);
        return ApiResponse.success(204, "노쇼유저가 삭되었습니다.", null);
    }

}
