package com.techwiz5.techwiz5.dtos.expense;


import com.techwiz5.techwiz5.dtos.UserDTO;
import com.techwiz5.techwiz5.dtos.trip.TripDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExpenseDTO {
    private Long id;
    private String expenseCategory;
    private String note;
    private BigDecimal amountExpense;
    private String name;
    private UserDTO user;
    private Timestamp date;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String createdBy;
    private String modifiedBy;
}
