package com.techwiz5.techwiz5.services;

import com.techwiz5.techwiz5.dtos.expense.ExpenseDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.expense.CreateExpense;
import com.techwiz5.techwiz5.models.expense.EditExpense;


import java.util.List;

public interface ExpenseService {
//    List<ExpenseDTO> findAll();
//    List<ExpenseDTO> findAllByUser(User user);
    List<ExpenseDTO> create(CreateExpense createExpense, User user);
    ExpenseDTO update(EditExpense editExpense, User user);
    void delete(Long[] ids);
}
