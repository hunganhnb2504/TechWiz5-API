package com.techwiz5.techwiz5.services;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface CurrencyConversionService {
    BigDecimal convertCurrency(BigDecimal amount, String targetCurrency);
}

