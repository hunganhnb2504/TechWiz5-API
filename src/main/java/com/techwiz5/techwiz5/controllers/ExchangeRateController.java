package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.services.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final CurrencyExchangeService currencyExchangeService;

    @GetMapping("/rate")
    public ResponseEntity<BigDecimal> getExchangeRate(
            @RequestParam("target") String targetCurrency) {
        try {
            BigDecimal rate = currencyExchangeService.getExchangeRate(targetCurrency);
            return ResponseEntity.ok(rate);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
