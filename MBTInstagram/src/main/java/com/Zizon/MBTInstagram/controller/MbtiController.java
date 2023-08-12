package com.Zizon.MBTInstagram.controller;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.global.exception.LessThanTwoException;
import com.Zizon.MBTInstagram.pythonServerDto.PythonMbtiResponseDto;
import com.Zizon.MBTInstagram.global.exception.CustomException;
import com.Zizon.MBTInstagram.responseDto.*;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.service.MbtiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            PythonMbtiResponseDto predictResult = mbtiService.predictMbtiByInstagram(SnsType.INSTAGRAM, snsUrl);

            return responsePredictResult(predictResult);

        }catch (CustomException e) {
            log.error(String.valueOf((e.getHttpStatus())));
            ApiResponseDto response = new ExceptionResponseDto(e.getHttpStatus(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.resolve(e.getHttpStatus()));
        } catch (Exception e){
            log.error(e.getMessage());
            ApiResponseDto response = new ApiResponseDto(500, false, "서버 내 오류");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sns/instagram/chemistry")
    public ResponseEntity<ApiResponseDto> chemistryPredict(@Valid @RequestParam(required = false) String id0,
                                                           @Valid @RequestParam(required = false) String id1,
                                                           @Valid @RequestParam(required = false) String id2,
                                                           @Valid @RequestParam(required = false) String id3,
                                                           @Valid @RequestParam(required = false) String id4){
        try{
            List<String> idList = new ArrayList<>();
            if(id0 != null)
                idList.add(id0);
            if(id1 != null)
                idList.add(id1);
            if(id2 != null)
                idList.add(id2);
            if(id3 != null)
                idList.add(id3);
            if(id4 != null)
                idList.add(id4);

            if(idList.size()<2){
                throw new LessThanTwoException();
            }

            String chemistryJson = mbtiService.predictChemistryByInstagram(idList);

            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode jsonNode = objectMapper.readTree(chemistryJson);

            ChemistryResponseDto responseDto = new ChemistryResponseDto();
            responseDto.setStatus(200);
            responseDto.setSuccess(true);
            responseDto.setMessage("각 mbti및 궁합 조회 성공");
            responseDto.setData(jsonNode);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);

        } catch (CustomException e) {
            log.error(String.valueOf((e.getHttpStatus())));
            ApiResponseDto response = new ExceptionResponseDto(e.getHttpStatus(), e.getMessage());
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
            PythonMbtiResponseDto predictResult = mbtiService.predictMbtiByText(text);

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

    private ResponseEntity<ApiResponseDto> responsePredictResult(PythonMbtiResponseDto predictResult) {
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
