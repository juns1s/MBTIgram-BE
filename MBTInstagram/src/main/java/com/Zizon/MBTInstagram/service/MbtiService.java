package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.requestDto.MbtiRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
@Slf4j
public class MbtiService {

    @Value("${Python.url}")
    private String pythonServerUrl;

    public String predictMbti(SnsType snsType, String url) throws Exception{

        log.info("SNS URL: " + url);

        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MbtiRequestDto requestDto = new MbtiRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();

        // Object Mapper를 통한 JSON 바인딩
        requestDto.setSnsUrl(url);
        String params = objectMapper.writeValueAsString(requestDto);
        JsonNode jsonNode = objectMapper.readTree(params);
        String snsUrl = jsonNode.get("snsUrl").asText();

        // HttpEntity에 헤더 설정
        HttpEntity entity = new HttpEntity(httpHeaders);

        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                pythonServerUrl + snsType.getSnsType() + "?snsUrl=" + snsUrl, String.class);

        // 요청 후 응답 확인
        log.info(responseEntity.getBody());
        HttpStatusCode statusCode = responseEntity.getStatusCode();
        if(statusCode.is4xxClientError()){
            throw new NoSuchFieldException();
        }
        else if(statusCode.is5xxServerError()){
            throw new RuntimeException();
        }

        // String to Object
        TestResponse response = objectMapper.readValue(responseEntity.getBody(), TestResponse.class);

        return response.mbti;
    }

    private static class TestResponse{
        public String mbti;
    }
}
