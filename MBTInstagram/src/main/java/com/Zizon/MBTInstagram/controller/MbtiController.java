package com.Zizon.MBTInstagram.controller;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.flaskDto.FlaskResponseDto;
import com.Zizon.MBTInstagram.global.MbtiType;
import com.Zizon.MBTInstagram.global.exception.CustomException;
import com.Zizon.MBTInstagram.responseDto.ApiResponseDto;
import com.Zizon.MBTInstagram.responseDto.ExceptionDto;
import com.Zizon.MBTInstagram.responseDto.MbtiPredictedDto;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.responseDto.RankingResponseDto;
import com.Zizon.MBTInstagram.service.MbtiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MbtiController {

    private final MbtiService mbtiService;

    @GetMapping("/sns/instagram")
    @Transactional
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
    @Transactional
    public ResponseEntity<ApiResponseDto> introductionPredict(@Valid @RequestParam String text) {
        try{
            FlaskResponseDto predictResult = mbtiService.predictMbtiByText(text);

            String mbtiResult = predictResult.getMbti();

            for (MbtiType type: MbtiType.values()) {
                if(type.mbti.equals(mbtiResult)){
                    mbtiService.addViews(type);
                }
            }

            return responsePredictResult(predictResult);
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rank")
    public ResponseEntity<RankingResponseDto> getRanking(){
        ArrayList<MbtiViews> mbtiViews = mbtiService.allMbtiOrderByCount();
        RankingResponseDto responseDto = new RankingResponseDto();
        Map<String, Integer> tmpMap = new HashMap<>();
        for(MbtiViews mbti: mbtiViews){
            tmpMap.put(mbti.getType().mbti, mbti.getCount());
        }
        responseDto.setStatus(200);
        responseDto.setSuccess(true);
        responseDto.setMessage("mbti별 랭킹 조회 성공");
        responseDto.setRank(sortByValuesDescending(tmpMap));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
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
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static <T extends Number & Comparable<? super T>> Map<String, T> sortByValuesDescending(Map<String, T> map) {
        List<Map.Entry<String, T>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.<String, T>comparingByValue().reversed());
        Map<String, T> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, T> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
