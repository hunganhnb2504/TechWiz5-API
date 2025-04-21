package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.entities.ExchangeRate;
import com.techwiz5.techwiz5.models.ExchangeRateResponse;
import com.techwiz5.techwiz5.repositories.ExchangeRateRepository;
import com.techwiz5.techwiz5.services.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeServiceImpl.class);

    private static final String EXTERNAL_API_URL = "https://v6.exchangerate-api.com/v6/e66fc655a318d26723c9988a/latest/USD";
    private static final Duration REFRESH_INTERVAL = Duration.ofHours(12);

    @Override
    public BigDecimal getExchangeRate(String targetCurrency) {
        String baseCurrency = "USD";
        Optional<ExchangeRate> rateOpt = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency);
        if (rateOpt.isPresent() && isRateFresh(rateOpt.get())) {
            logger.info("Returning cached exchange rate for {} to {}", baseCurrency, targetCurrency);
            return rateOpt.get().getRate();
        }
        ExchangeRateResponse response = restTemplate.getForObject(EXTERNAL_API_URL, ExchangeRateResponse.class);

        if (response == null || response.getConversion_rates() == null) {
            logger.error("Failed to retrieve exchange rate data for USD");
            throw new RuntimeException("Exchange rate data not found for USD");
        }

        BigDecimal rate = response.getConversion_rates().get(targetCurrency);
        if (rate == null) {
            logger.error("Exchange rate not found for USD to {}", targetCurrency);
            throw new RuntimeException("Exchange rate not found for USD to " + targetCurrency);
        }

        // Update or insert the new exchange rate into the database
        updateExchangeRate(baseCurrency, targetCurrency, rate);
        logger.info("Fetched and saved exchange rate for {} to {}", baseCurrency, targetCurrency);
        return rate;
    }

    @Override
    public void updateExchangeRate(String baseCurrency, String targetCurrency, BigDecimal rate) {
        Optional<ExchangeRate> existingRateOpt = exchangeRateRepository.findByBaseCurrencyAndTargetCurrency(baseCurrency, targetCurrency);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        if (existingRateOpt.isPresent()) {
            ExchangeRate existingRate = existingRateOpt.get();
            existingRate.setRate(rate);
            existingRate.setLastUpdated(currentTime);
            exchangeRateRepository.save(existingRate);
            logger.info("Updated exchange rate for {} to {}", baseCurrency, targetCurrency);
        } else {
            ExchangeRate newRate = new ExchangeRate(baseCurrency, targetCurrency, rate);
            newRate.setLastUpdated(currentTime);
            exchangeRateRepository.save(newRate);
            logger.info("Saved new exchange rate for {} to {}", baseCurrency, targetCurrency);
        }
    }

    private boolean isRateFresh(ExchangeRate rate) {
        Instant lastUpdated = rate.getLastUpdated().toInstant();
        Instant now = Instant.now();
        return Duration.between(lastUpdated, now).compareTo(REFRESH_INTERVAL) < 0;
    }
}
