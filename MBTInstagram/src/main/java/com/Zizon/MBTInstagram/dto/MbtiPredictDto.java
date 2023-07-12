package com.Zizon.MBTInstagram.dto;

import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class MbtiPredictDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SnsType snsType;
}
