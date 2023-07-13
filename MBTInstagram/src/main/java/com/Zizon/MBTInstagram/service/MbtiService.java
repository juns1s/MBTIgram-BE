package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.requestDto.MbtiRequestDto;
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
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MbtiRequestDto requestDto = new MbtiRequestDto();
        ObjectMapper objectMapper = new ObjectMapper();

        // Object Mapper를 통한 JSON 바인딩
        requestDto.setSnsUrl(url);
        String params = objectMapper.writeValueAsString(requestDto);

        // HttpEntity에 헤더 및 params 설정
        HttpEntity entity = new HttpEntity(params, httpHeaders);

        // RestTemplate의 exchange 메소드를 통해 URL에 HttpEntity와 함께 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(pythonServerUrl+snsType.getSnsType(), HttpMethod.POST,
                entity, String.class);

        // 요청 후 응답 확인
        log.info("SNS URL: "+url);
        log.info(responseEntity.getBody());

        // String to Object
        TestResponse response = objectMapper.readValue(responseEntity.getBody(), TestResponse.class);
        return response.mbti;
    }

    private static class TestResponse{
        public String mbti;
    }
}
