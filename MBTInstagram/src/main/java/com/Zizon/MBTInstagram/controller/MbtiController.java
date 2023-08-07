package com.Zizon.MBTInstagram.controller;

import com.Zizon.MBTInstagram.flaskDto.FlaskResponseDto;
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/sns/instagram")
    public ResponseEntity<ApiResponseDto> instagramPredict(@Valid @RequestParam String snsUrl){
        try{
            FlaskResponseDto predictResult = mbtiService.predictMbtiByInstagram(SnsType.INSTAGRAM, snsUrl);

            return responsePredictResult(predictResult);

        }catch (CustomException e) {
            log.error(String.valueOf((e.getHttpStatus())));
            ApiResponseDto response = new ExceptionDto(e.getHttpStatus(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.resolve(e.getHttpStatus()));
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sns/introduction")
    public ResponseEntity<ApiResponseDto> introductionPredict(@Valid @RequestParam String text) {
        try{
            FlaskResponseDto predictResult = mbtiService.predictMbtiByText(text);

            return responsePredictResult(predictResult);
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/healthCheck")
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck(){}

    private ResponseEntity<ApiResponseDto> responsePredictResult(FlaskResponseDto predictResult) {
        MbtiPredictedDto response = new MbtiPredictedDto();
        response.setStatus(200);
        response.setSuccess(true);
        response.setSuccess(true);
        response.setMessage("인스타그램 mbti 분석 성공");
        response.setMbti(predictResult.getMbti());
        response.setProb(sortByValuesDescending(predictResult.getProb()));
        System.out.println(sortByValuesDescending(predictResult.getProb()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static Map<String, Double> sortByValuesDescending(Map<String, Double> map) {
        List<Map.Entry<String, Double>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.<String, Double>comparingByValue().reversed());
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
