package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.services.CurrencyExchangeService;
import com.techwiz5.techwiz5.services.CurrencyConversionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ICurrencyConversionService implements CurrencyConversionService {

    private final CurrencyExchangeService currencyExchangeService;

    public ICurrencyConversionService(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @Override
    public BigDecimal convertCurrency(BigDecimal amount, String targetCurrency) {
        BigDecimal rate = currencyExchangeService.getExchangeRate(targetCurrency);
        return amount.multiply(rate);
    }
}
