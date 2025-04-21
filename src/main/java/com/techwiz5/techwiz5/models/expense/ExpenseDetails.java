package com.techwiz5.techwiz5.models.expense;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDetails {
    private String expenseCategory;
    private String note;
    private String name;
    private BigDecimal amountExpense;
    private Timestamp date;
}
