package com.expenseiq.expenseiq.controller;

import com.expenseiq.expenseiq.dto.BudgetDTO;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public ResponseEntity<List<BudgetDTO>> getBudgets(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String monthYear) {
        return ResponseEntity.ok(budgetService.getBudgets(user, monthYear));
    }

    @PostMapping
    public ResponseEntity<BudgetDTO> setBudget(@Valid @RequestBody BudgetDTO dto, @AuthenticationPrincipal User user) {
        BudgetDTO created = budgetService.setBudget(dto, user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BudgetDTO> updateBudget(@Valid @RequestBody BudgetDTO dto, @AuthenticationPrincipal User user) {
        BudgetDTO updated = budgetService.setBudget(dto, user);
        return ResponseEntity.ok(updated);
    }
}
