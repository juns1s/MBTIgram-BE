package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.flaskDto.FlaskResponseDto;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.global.exception.NoAccountException;
import com.Zizon.MBTInstagram.global.exception.NoPostException;
import com.Zizon.MBTInstagram.global.exception.PrivateAccountException;
import com.Zizon.MBTInstagram.flaskDto.MbtiInstagramRequestDto;
import com.Zizon.MBTInstagram.flaskDto.MbtiTextRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class MbtiService {

    @Value("${Python.url}")
    private String pythonServerUrl;

    public FlaskResponseDto predictMbtiByInstagram(SnsType snsType, String url) throws Exception {

        log.info("SNS URL: " + url);

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

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
            FlaskResponseDto response = objectMapper.readValue(responseEntity.getBody(), FlaskResponseDto.class);

            return response;
        } catch (Exception e) {
            String httpStatus = e.getMessage().substring(0,3);

            if(httpStatus.equals("400")){
                throw new NoPostException();
            }
            else if(httpStatus.equals("404")){
                throw new NoAccountException();
            }
            else if(httpStatus.equals("401")){
                throw new PrivateAccountException();
            }
            else if(httpStatus.equals("500")){
                throw new RuntimeException();
            }
        }
        return null;
    }

    public FlaskResponseDto predictMbtiByText(String text) throws Exception {
        log.info("Text: " + text);

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

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
            FlaskResponseDto response = objectMapper.readValue(responseEntity.getBody(), FlaskResponseDto.class);

            return response;
        } catch (Exception e) {
            String httpStatus = e.getMessage().substring(0,3);

            if(httpStatus.equals("500")){
                throw new RuntimeException();
            }
        }
        return null;
    }
}
