package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findByBaseCurrencyAndTargetCurrency(String baseCurrency, String targetCurrency);
}
