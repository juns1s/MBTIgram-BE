package com.Zizon.MBTInstagram.service;

import com.Zizon.MBTInstagram.global.embedded.SnsType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MbtiService {

    public String predictMbti(SnsType snsType, String url){
        System.out.println("분석하기");
        String mbti = "ABCD";
        return mbti;
    }
}
