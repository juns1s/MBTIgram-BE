package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.domain.MbtiViews;
import com.Zizon.MBTInstagram.pythonServerDto.PythonMbtiResponseDto;
import com.Zizon.MBTInstagram.global.MbtiType;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.Zizon.MBTInstagram.global.exception.NoAccountException;
import com.Zizon.MBTInstagram.global.exception.NoPostException;
import com.Zizon.MBTInstagram.global.exception.PrivateAccountException;
import com.Zizon.MBTInstagram.repository.MbtiViewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MbtiService {

    private final MbtiViewsRepository mbtiTypeRepository;

    @Value("${Python.url}")
    private String pythonServerUrl;

    @Transactional
    public PythonMbtiResponseDto predictMbtiByInstagram(SnsType snsType, String url) throws Exception {

        log.info("SNS URL: " + url);
        ObjectMapper objectMapper = new ObjectMapper();

        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    pythonServerUrl + snsType.getSnsType() + "?snsUrl=" + url, String.class);

            // 요청 후 응답 확인
            log.info(responseEntity.getBody());

            // JSON을 클래스로 변경
            PythonMbtiResponseDto responseDto = objectMapper.readValue(responseEntity.getBody(), PythonMbtiResponseDto.class);
            MbtiType mbtiType = MbtiType.fromString(responseDto.getMbti());
            addViews(mbtiType);
            return responseDto;
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

    @Transactional
    public String predictChemistryByInstagram(List<String> idLIst) throws Exception {
        int idCnt = idLIst.size();
        StringBuilder queryString = new StringBuilder();
        queryString.append("?");

        for(int i=0; i<idCnt-1; i++){
            queryString.append("id");
            queryString.append(i);
            queryString.append("=");
            queryString.append(idLIst.get(i));
            queryString.append("&");
        }
        queryString.append("id");
        queryString.append(idCnt-1);
        queryString.append("=");
        queryString.append(idLIst.get(idCnt-1));

        System.out.println("queryString = " + queryString);
        
        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    pythonServerUrl + "/chemistry" + queryString, String.class);

            // 요청 후 응답 확인
            log.info(responseEntity.getBody());

            return responseEntity.getBody();
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


    public PythonMbtiResponseDto predictMbtiByText(String text){
        log.info("Text: " + text);

        ObjectMapper objectMapper = new ObjectMapper();


        // 파이썬 서버에 쿼리스트링을 통해 URL 분석 GET 요청
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    pythonServerUrl + "introduction" + "?text=" + text, String.class);

            // 요청 후 응답 확인
            log.info(responseEntity.getBody());

            // String to Object
            return objectMapper.readValue(responseEntity.getBody(), PythonMbtiResponseDto.class);
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

    @Transactional
    public int addViews(MbtiType mbtiType){
        Optional<MbtiViews> optionalMbtiViews = mbtiTypeRepository.findByType(mbtiType);
        MbtiViews mbtiViews = optionalMbtiViews.get();
        mbtiViews.addCount();
        return mbtiViews.getCount();
    }
}
