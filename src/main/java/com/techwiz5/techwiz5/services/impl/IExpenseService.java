package com.techwiz5.techwiz5.services.impl;

import com.techwiz5.techwiz5.dtos.expense.ExpenseDTO;
import com.techwiz5.techwiz5.entities.Expense;
import com.techwiz5.techwiz5.entities.Trip;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import com.techwiz5.techwiz5.mappers.ExpenseMapper;
import com.techwiz5.techwiz5.models.expense.CreateExpense;
import com.techwiz5.techwiz5.models.expense.EditExpense;
import com.techwiz5.techwiz5.repositories.ExpenseRepository;
import com.techwiz5.techwiz5.repositories.TripRepository;
import com.techwiz5.techwiz5.services.ExpenseService;
import com.techwiz5.techwiz5.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.techwiz5.techwiz5.models.expense.ExpenseDetails;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IExpenseService implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final TripRepository tripRepository;
    private final NotificationService notificationService;

//    @Override
//    public List<ExpenseDTO> findAll() {
//        return expenseRepository.findAll().stream().map(expenseMapper::toExpenseDTO).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<ExpenseDTO> findAllByUser(User user) {
//        List<Expense> categories = expenseRepository.findAllByUser(user);
//        return categories.stream()
//                .map(expenseMapper::toExpenseDTO)
//                .collect(Collectors.toList());
//    }


    @Override
    public List<ExpenseDTO> create(CreateExpense createExpense, User user) {
        Trip trip = tripRepository.findById(createExpense.getTripId())
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOTFOUND));
        List<ExpenseDTO> createdExpenses = new ArrayList<>();
        BigDecimal currentTotalExpense = expenseRepository.sumExpensesForTrip(trip);
        if (currentTotalExpense == null) {
            currentTotalExpense = BigDecimal.ZERO;
        }
        for (ExpenseDetails details : createExpense.getExpenses()) {
            LocalDate expenseDate = details.getDate().toLocalDateTime().toLocalDate();
            LocalDate tripStartDate = trip.getStartDate().toLocalDateTime().toLocalDate();
            LocalDate tripEndDate = trip.getEndDate().toLocalDateTime().toLocalDate();
            if (expenseDate.isBefore(tripStartDate) || expenseDate.isAfter(tripEndDate)) {
                throw new AppException(ErrorCode.INVALID_DATE);
            }

            Expense expense = Expense.builder()
                    .trip(trip)
                    .user(user)
                    .expenseCategory(details.getExpenseCategory())
                    .note(details.getNote())
                    .amountExpense(details.getAmountExpense())
                    .date(details.getDate())
                    .name(details.getName())
                    .createdBy(user.getFullName())
                    .modifiedBy(user.getFullName())
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .modifiedDate(new Timestamp(System.currentTimeMillis()))
                    .build();

            expenseRepository.save(expense);
            createdExpenses.add(expenseMapper.toExpenseDTO(expense));
            currentTotalExpense = currentTotalExpense.add(details.getAmountExpense());
            if (currentTotalExpense.compareTo(trip.getBudget()) > 0) {
                notificationService.sendBudgetAlert(user, trip.getTripName());
            }
        }
        return createdExpenses;
    }

    @Override
    public ExpenseDTO update(EditExpense editExpense, User user) {

        Expense expense = expenseRepository.findById(editExpense.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        Trip trip = tripRepository.findById(editExpense.getTripId())
                .orElseThrow(() -> new AppException(ErrorCode.TRIP_NOTFOUND));

        if (expense.getDate().before(trip.getStartDate()) || expense.getDate().after(trip.getEndDate())) {
            throw new AppException(ErrorCode.INVALID_DATE);
        }
        BigDecimal currentTotalExpense = expenseRepository.sumExpensesForTrip(trip);
        expense.setName(editExpense.getName());
        expense.setDate(editExpense.getDate());
        expense.setAmountExpense(editExpense.getAmountExpense());
        expense.setNote(editExpense.getNote());
        expense.setExpenseCategory(editExpense.getExpenseCategory());
        if (!expense.getTrip().getId().equals(editExpense.getTripId())) {
            expense.setTrip(trip);
        }
        expense.setCreatedBy(user.getFullName());
        expense.setModifiedBy(user.getFullName());
        expense.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        expense.setModifiedDate(new Timestamp(System.currentTimeMillis()));
        expenseRepository.save(expense);

        currentTotalExpense = currentTotalExpense.subtract(expense.getAmountExpense()).add(editExpense.getAmountExpense());
        if (currentTotalExpense.compareTo(trip.getBudget()) > 0) {
            notificationService.sendBudgetAlert(user, trip.getTripName());
        }

        return expenseMapper.toExpenseDTO(expense);
    }



    @Override
    public void delete(Long[] ids) {
        expenseRepository.deleteAllById(List.of(ids));
    }
}
