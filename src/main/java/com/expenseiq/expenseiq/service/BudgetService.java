package com.expenseiq.expenseiq.service;

import com.expenseiq.expenseiq.dto.BudgetDTO;
import com.expenseiq.expenseiq.dto.CategoryBudgetStatusDTO;
import com.expenseiq.expenseiq.entity.Budget;
import com.expenseiq.expenseiq.entity.Category;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.exception.ResourceNotFoundException;
import com.expenseiq.expenseiq.repository.BudgetRepository;
import com.expenseiq.expenseiq.repository.CategoryRepository;
import com.expenseiq.expenseiq.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<BudgetDTO> getBudgets(User user, String monthYear) {
        List<Budget> budgets;
        if (monthYear != null && !monthYear.isBlank()) {
            budgets = budgetRepository.findByUserIdAndMonthYear(user.getId(), monthYear);
        } else {
            budgets = budgetRepository.findByUserId(user.getId());
        }
        return budgets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BudgetDTO setBudget(BudgetDTO dto, User user) {
        Category category = categoryRepository.findByIdAndAvailableForUser(dto.getCategoryId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Optional<Budget> existingOpt = budgetRepository.findByUserIdAndCategoryIdAndMonthYear(
                user.getId(), dto.getCategoryId(), dto.getMonthYear());

        Budget budget;
        if (existingOpt.isPresent()) {
            budget = existingOpt.get();
            budget.setLimitAmount(dto.getLimitAmount());
        } else {
            budget = new Budget();
            budget.setUser(user);
            budget.setCategory(category);
            budget.setMonthYear(dto.getMonthYear());
            budget.setLimitAmount(dto.getLimitAmount());
        }

        Budget saved = budgetRepository.save(budget);
        return convertToDTO(saved);
    }

    public List<CategoryBudgetStatusDTO> getBudgetStatusList(User user, String monthYear) {
        YearMonth ym = (monthYear != null && !monthYear.isBlank()) ? YearMonth.parse(monthYear) : YearMonth.now();
        String currentMonthYearStr = ym.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        LocalDate startDate = ym.atDay(1);
        LocalDate endDate = ym.atEndOfMonth();

        List<Budget> budgets = budgetRepository.findByUserIdAndMonthYear(user.getId(), currentMonthYearStr);

        return budgets.stream().map(b -> {
            BigDecimal spent = transactionRepository.getCategoryExpenseSumForPeriod(
                    user.getId(), b.getCategory().getId(), startDate, endDate);

            if (spent == null) {
                spent = BigDecimal.ZERO;
            }

            BigDecimal remaining = b.getLimitAmount().subtract(spent);
            boolean isExceeded = spent.compareTo(b.getLimitAmount()) > 0;

            double percentageUsed = 0.0;
            if (b.getLimitAmount().compareTo(BigDecimal.ZERO) > 0) {
                percentageUsed = spent.multiply(BigDecimal.valueOf(100))
                        .divide(b.getLimitAmount(), 2, RoundingMode.HALF_UP)
                        .doubleValue();
            }

            return new CategoryBudgetStatusDTO(
                    b.getCategory().getId(),
                    b.getCategory().getName(),
                    currentMonthYearStr,
                    b.getLimitAmount(),
                    spent,
                    remaining,
                    isExceeded,
                    percentageUsed
            );
        }).collect(Collectors.toList());
    }

    private BudgetDTO convertToDTO(Budget budget) {
        return new BudgetDTO(
                budget.getId(),
                budget.getCategory().getId(),
                budget.getCategory().getName(),
                budget.getMonthYear(),
                budget.getLimitAmount()
        );
    }
}
