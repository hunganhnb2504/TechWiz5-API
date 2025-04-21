package com.techwiz5.techwiz5.repositories;

import com.techwiz5.techwiz5.entities.Category;
import com.techwiz5.techwiz5.entities.Expense;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUser(User user);
    @Query("SELECT SUM(e.amountExpense) FROM Expense e WHERE e.trip = :trip")
    BigDecimal sumExpensesForTrip(Trip trip);
}
