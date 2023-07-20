package com.Zizon.MBTInstagram.controller;

import com.Zizon.MBTInstagram.global.exception.CustomException;
import com.Zizon.MBTInstagram.responseDto.ApiResponseDto;
import com.Zizon.MBTInstagram.responseDto.ExceptionDto;
import com.Zizon.MBTInstagram.responseDto.MbtiPredictedDto;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.service.MbtiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/sns/instagram")
    public ResponseEntity<ApiResponseDto> instagramPredict(@Valid @RequestParam String snsUrl){
        try{
            String mbti = mbtiService.predictMbtiByInstagram(SnsType.INSTAGRAM, snsUrl);

            MbtiPredictedDto response = new MbtiPredictedDto();
            response.setStatus(200);
            response.setSuccess(true);
            response.setSuccess(true);
            response.setMessage("인스타그램 mbti 분석 성공");
            response.setMbti(mbti);
            return new ResponseEntity<>(response, HttpStatus.OK);

        }catch (CustomException e) {
            log.error(String.valueOf((e.getHttpStatus())));
            ApiResponseDto response = new ExceptionDto(e.getHttpStatus(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.resolve(e.getHttpStatus()));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sns/introduction")
    public ResponseEntity<ApiResponseDto> introductionPredict(@Valid @RequestParam String text) throws Exception {
        try{
            String mbti = mbtiService.predictMbtiByText(text);

            MbtiPredictedDto response = new MbtiPredictedDto();
            response.setStatus(200);
            response.setSuccess(true);
            response.setSuccess(true);
            response.setMessage("인스타그램 mbti 분석 성공");
            response.setMbti(mbti);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/healthCheck")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck(){}
}
