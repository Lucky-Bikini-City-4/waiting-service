package com.dayaeyak.waiting.domain.service;

import com.dayaeyak.waiting.domain.dto.request.NoShowCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.request.WaitingCreateRequestDto;
import com.dayaeyak.waiting.domain.dto.response.*;
import com.dayaeyak.waiting.domain.entity.NoShow;
import com.dayaeyak.waiting.domain.entity.Waiting;
import com.dayaeyak.waiting.domain.enums.WaitingStatus;
import com.dayaeyak.waiting.domain.repository.jpa.NoShowRepository;
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
public class NoShowService {

    private final NoShowRepository noShowRepository;


    public NoShowCreateResponseDto register(NoShowCreateRequestDto requestDto){
        NoShow noshow = NoShow.builder()
                .restaurantId(requestDto.getRestaurantId())
                .userId(requestDto.getUserId())
                .build();
        NoShow savedNoShow = noShowRepository.save(noshow);

        return new NoShowCreateResponseDto(savedNoShow.getNoShowId());
    }

    public NoShowResponseDto getNoShow(BigInteger noShowID){
        NoShow noshow = noShowRepository.findById(noShowID)
                .orElseThrow(() -> new EntityNotFoundException());
        return NoShowResponseDto.from(noshow);
    }

    public NoShowListResponseDto getNoShows(
            BigInteger restaurantId,
            int page,
            int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NoShow> result = noShowRepository.findByRestaurantIdAndDeletedAtIsNull(restaurantId, pageable);
        List<NoShowResponseDto> data = result.getContent().stream()
                .map(NoShowResponseDto::from)
                .toList();

        return new NoShowListResponseDto(result.getTotalElements(), data);
    }

    public void deleteNoShow(BigInteger noShowID){
        NoShow noshow = noShowRepository.findById(noShowID)
                .orElseThrow(() -> new EntityNotFoundException());

        if (noshow.getDeletedAt() != null) return;
        noshow.delete();
    }

    public void deleteNoShowAll(BigInteger restaurantId){
        List<NoShow> noshowList = noShowRepository.findByRestaurantIdAndDeletedAtIsNull(restaurantId);
        if (noshowList.isEmpty()) return;

        for (NoShow noshow : noshowList){
            if (noshow.getDeletedAt()==null) {
                noshow.delete();
            }
        }
    }
}
