package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.flaskDto.FlaskResponseDto;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.global.exception.NoAccountException;
import com.Zizon.MBTInstagram.global.exception.NoPostException;
import com.Zizon.MBTInstagram.global.exception.PrivateAccountException;
import com.Zizon.MBTInstagram.flaskDto.MbtiInstagramRequestDto;
import com.Zizon.MBTInstagram.flaskDto.MbtiTextRequestDto;
import com.Zizon.MBTInstagram.repository.MbtiViewsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class MbtiService {

    private final MbtiViewsRepository mbtiTypeRepository;

    @Value("${Python.url}")
    private String pythonServerUrl;

    public FlaskResponseDto predictMbtiByInstagram(SnsType snsType, String url) throws Exception {

        log.info("SNS URL: " + url);

        MbtiInstagramRequestDto requestDto = new MbtiInstagramRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();

        // Object Mapper를 통한 JSON 바인딩
        requestDto.setSnsUrl(url);
        String params = objectMapper.writeValueAsString(requestDto);
        JsonNode jsonNode = objectMapper.readTree(params);
        String snsUrl = jsonNode.get("snsUrl").asText();

        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    pythonServerUrl + snsType.getSnsType() + "?snsUrl=" + snsUrl, String.class);

            // 요청 후 응답 확인
            log.info(responseEntity.getBody());

            // JSON을 클래스로 변경

            return objectMapper.readValue(responseEntity.getBody(), FlaskResponseDto.class);
        } catch (Exception e) {
            String httpStatus = e.getMessage().substring(0,3);

            switch (httpStatus) {
                case "400" -> throw new NoPostException();
                case "404" -> throw new NoAccountException();
                case "401" -> throw new PrivateAccountException();
                case "500" -> throw new RuntimeException();
                default -> {
                }
            }
        }
        return null;
    }

    public FlaskResponseDto predictMbtiByText(String text) throws Exception {
        log.info("Text: " + text);

        MbtiTextRequestDto requestDto = new MbtiTextRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();

        // Object Mapper를 통한 JSON 바인딩
        requestDto.setText(text);
        String params = objectMapper.writeValueAsString(requestDto);
        JsonNode jsonNode = objectMapper.readTree(params);
        String introduction = jsonNode.get("text").asText();

        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    pythonServerUrl + "introduction" + "?text=" + introduction, String.class);

            // 요청 후 응답 확인
            log.info(responseEntity.getBody());

            // String to Object

            return objectMapper.readValue(responseEntity.getBody(), FlaskResponseDto.class);
        } catch (Exception e) {
            String httpStatus = e.getMessage().substring(0,3);

            if(httpStatus.equals("500")){
                throw new RuntimeException();
            }
        }
        return null;
    }

    public ArrayList<MbtiViews> allMbtiOrderByCount(){
        return mbtiTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "count"));
    }
}
