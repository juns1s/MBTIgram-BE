package com.Zizon.MBTInstagram.dto;

import com.Zizon.MBTInstagram.global.embedded.SnsType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class MbtiRegisterDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private SnsType snsType;
    private String snsAddress;
}
