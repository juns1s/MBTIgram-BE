package com.Zizon.MBTInstagram.domain;

import com.Zizon.MBTInstagram.dto.UserMbtiRegisterDto;
import com.Zizon.MBTInstagram.global.embedded.MbtiType;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class UserMbti {
    @Id
    @GeneratedValue
    @Column(name = "userMbti_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SnsType snsType;

    @NotNull
    private String snsAddress;

    @Enumerated(EnumType.STRING)
    private MbtiType mbtiType;

    private LocalDateTime predictTime;

    public UserMbti(UserMbtiRegisterDto registerDto) {
        this.snsType = registerDto.getSnsType();
        this.snsAddress = registerDto.getSnsAddress();
    }

    public MbtiType updateMbtiType(MbtiType mbti){
        this.mbtiType = mbti;
        return mbtiType;
    }

    public void updatePredictTime(){
        predictTime = LocalDateTime.now();
    }

}
