package com.Zizon.MBTInstagram.dto;

import com.Zizon.MBTInstagram.domain.UserMbti;
import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class UserMbtiPredictDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SnsType snsType;

    public UserMbtiPredictDto(UserMbti userMbti) {
        this.id = userMbti.getId();
        this.snsType = userMbti.getSnsType();
    }
}
