package com.Zizon.MBTInstagram.responseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Data
@NoArgsConstructor
public class RankingResponseDto extends ApiResponseDto{
    private Map<String, Integer> rank;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RankingResponseDto that = (RankingResponseDto) o;
        return Objects.equals(getRank(), that.getRank());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getRank());
    }
}
