package com.techwiz5.techwiz5.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@Table(name = "exchange_rates")
public class ExchangeRate extends BaseEntity {

    @Column(name = "base_currency", nullable = false)
    private String baseCurrency;

    @Column(name = "target_currency", nullable = false)
    private String targetCurrency;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "last_updated", nullable = false)
    private Timestamp lastUpdated;

    public ExchangeRate(String baseCurrency, String targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
        this.lastUpdated = new Timestamp(System.currentTimeMillis());
    }
}
