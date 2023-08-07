package com.Zizon.MBTInstagram.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
public class MbtiPredictedDto extends ApiResponseDto{
    private String mbti;
    private Map<String, Double> prob;

    public MbtiPredictedDto(int status, boolean success, String message, String mbti) {
        super(status, success, message);
        this.mbti = mbti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MbtiPredictedDto that = (MbtiPredictedDto) o;
        return Objects.equals(getMbti(), that.getMbti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getMbti());
    }
}
