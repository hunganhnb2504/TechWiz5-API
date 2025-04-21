package com.techwiz5.techwiz5.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Getter
@Setter
public class ExchangeRateResponse {
    private String result;
    private String base_code;
    private Map<String, BigDecimal> conversion_rates;
}
