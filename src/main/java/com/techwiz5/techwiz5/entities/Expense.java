package com.techwiz5.techwiz5.entities;

import jakarta.persistence.*;
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
@Table(name = "expense")
public class Expense extends   BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "expense_category", length = 100)
    private String expenseCategory;

    @Column(name = "note")
    private String note;

    @Column(name ="name")
    private String name;

    @Column(name = "amount_expense", precision = 10, scale = 2)
    private BigDecimal amountExpense;

    @Column(name = "date")
    private Timestamp date;

}

