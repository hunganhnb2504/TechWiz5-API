package com.techwiz5.techwiz5.services;

import java.math.BigDecimal;

public interface CurrencyExchangeService {
    BigDecimal getExchangeRate( String targetCurrency);
    void updateExchangeRate(String baseCurrency, String targetCurrency, BigDecimal rate);
}
