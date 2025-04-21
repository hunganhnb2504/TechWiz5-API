package com.techwiz5.techwiz5.models.expense;

import lombok.*;
import java.util.List;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExpense {
    private List<ExpenseDetails> expenses;
    private long tripId;
}
