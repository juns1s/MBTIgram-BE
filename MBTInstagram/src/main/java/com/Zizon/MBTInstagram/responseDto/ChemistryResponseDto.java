package com.Zizon.MBTInstagram.responseDto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ChemistryResponseDto extends ApiResponseDto{
    private JsonNode data;
}
