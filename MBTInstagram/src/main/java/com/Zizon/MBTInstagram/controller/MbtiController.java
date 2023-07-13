package com.Zizon.MBTInstagram.controller;

import com.Zizon.MBTInstagram.requestDto.MbtiRequestDto;
import com.Zizon.MBTInstagram.responseDto.ApiResponseDto;
import com.Zizon.MBTInstagram.responseDto.MbtiPredictedDto;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.service.MbtiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/sns/instagram")
    public ResponseEntity<ApiResponseDto> instagramPredict(@Valid @RequestBody MbtiRequestDto dto){
        try{
            String mbti = mbtiService.predictMbti(SnsType.INSTAGRAM, dto.getSnsUrl());

            MbtiPredictedDto response = new MbtiPredictedDto();
            response.setStatus(200);
            response.setSuccess(true);
            response.setSuccess(true);
            response.setMessage("인스타그램 mbti 분석 성공");
            response.setMbti(mbti);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (Exception e) {
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(400, false, "계정 조회 불가");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
