package com.techwiz5.techwiz5.controllers;

import com.techwiz5.techwiz5.dtos.ResponseObject;
import com.techwiz5.techwiz5.dtos.expense.ExpenseDTO;
import com.techwiz5.techwiz5.entities.User;
import com.techwiz5.techwiz5.models.expense.CreateExpense;
import com.techwiz5.techwiz5.models.expense.EditExpense;
import com.techwiz5.techwiz5.services.CurrencyConversionService;
import com.techwiz5.techwiz5.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.techwiz5.techwiz5.exceptions.AppException;
import com.techwiz5.techwiz5.exceptions.ErrorCode;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;
    private final CurrencyConversionService currencyConversionService;


    @GetMapping("/convert")
    public ResponseEntity<?> convertExpense(
            @RequestParam("amount") BigDecimal amount,
            @RequestParam(value = "targetCurrency", required = false) String targetCurrency) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        if (targetCurrency == null || targetCurrency.isEmpty()) {
            targetCurrency = currentUser.getPreferredCurrency();
            if (targetCurrency == null || targetCurrency.isEmpty()) {
                throw new AppException(ErrorCode.PREFERRED_CURRENCY_NOTFOUND);
            }
        }
        BigDecimal convertedAmount = currencyConversionService.convertCurrency(amount, targetCurrency);
        return ResponseEntity.ok(convertedAmount);
    }

//    @GetMapping("/any/expense")
//    ResponseEntity<ResponseObject> getAll() {
//        List<ExpenseDTO> list = expenseService.findAll();
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(true, 200, "ok", list)
//        );
//    }
//    @GetMapping("/expense")
//    ResponseEntity<ResponseObject> getAllByUser() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User currentUser = (User) auth.getPrincipal();
//        List<ExpenseDTO> list = expenseService.findAllByUser(currentUser);
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(true, 200, "ok", list)
//        );
//    }
    @PostMapping("/expense")
    ResponseEntity<ResponseObject> create(@Valid @RequestBody CreateExpense createExpense) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<ExpenseDTO> expenseDTO= expenseService.create(createExpense, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Create Success", expenseDTO)
        );
    }
    @PutMapping("/expense")
    ResponseEntity<ResponseObject> update(@Valid @RequestBody EditExpense editExpense) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        ExpenseDTO expenseDTO = expenseService.update(editExpense, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Update Success", expenseDTO)
        );
    }

    @DeleteMapping("/expense")
    ResponseEntity<ResponseObject> update(@RequestBody Long[] ids) {
        expenseService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "Delete success", "")
        );
    }
}
