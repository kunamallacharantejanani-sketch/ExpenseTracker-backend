package com.expenseiq.expenseiq.service;

import com.expenseiq.expenseiq.dto.AnalyticsDTO;
import com.expenseiq.expenseiq.dto.CategoryBudgetStatusDTO;
import com.expenseiq.expenseiq.dto.DashboardSummaryDTO;
import com.expenseiq.expenseiq.entity.TransactionType;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BudgetService budgetService;

    public DashboardSummaryDTO getSummary(User user) {
        BigDecimal totalIncome = transactionRepository.getTotalAmountByType(user.getId(), TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.getTotalAmountByType(user.getId(), TransactionType.EXPENSE);
        BigDecimal balance = totalIncome.subtract(totalExpense);

        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        BigDecimal currentMonthSpend = transactionRepository.getAmountByTypeAndDateRange(
                user.getId(), TransactionType.EXPENSE, startDate, endDate);

        String currentMonthStr = currentMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<CategoryBudgetStatusDTO> budgetStatusList = budgetService.getBudgetStatusList(user, currentMonthStr);

        return new DashboardSummaryDTO(totalIncome, totalExpense, balance, currentMonthSpend, budgetStatusList);
    }

    public AnalyticsDTO getAnalytics(User user) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate monthStart = currentMonth.atDay(1);
        LocalDate monthEnd = currentMonth.atEndOfMonth();

        // 1. Category Breakdown for current month expenses
        List<Object[]> categorySums = transactionRepository.getCategorySumByDateRange(
                user.getId(), TransactionType.EXPENSE, monthStart, monthEnd);

        List<AnalyticsDTO.CategoryExpenseItem> categoryBreakdown = new ArrayList<>();
        for (Object[] row : categorySums) {
            String categoryName = (String) row[1];
            BigDecimal sum = (BigDecimal) row[2];
            categoryBreakdown.add(new AnalyticsDTO.CategoryExpenseItem(categoryName, sum));
        }

        // 2. Monthly Trend (Past 6 Months)
        List<AnalyticsDTO.MonthlyTrendItem> monthlyTrend = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = currentMonth.minusMonths(i);
            LocalDate start = ym.atDay(1);
            LocalDate end = ym.atEndOfMonth();
            String ymStr = ym.format(DateTimeFormatter.ofPattern("MMM yyyy"));

            BigDecimal inc = transactionRepository.getAmountByTypeAndDateRange(user.getId(), TransactionType.INCOME, start, end);
            BigDecimal exp = transactionRepository.getAmountByTypeAndDateRange(user.getId(), TransactionType.EXPENSE, start, end);

            monthlyTrend.add(new AnalyticsDTO.MonthlyTrendItem(ymStr, inc, exp));
        }

        // 3. Overall Income vs Expense
        BigDecimal totalIncome = transactionRepository.getTotalAmountByType(user.getId(), TransactionType.INCOME);
        BigDecimal totalExpense = transactionRepository.getTotalAmountByType(user.getId(), TransactionType.EXPENSE);
        AnalyticsDTO.IncomeVsExpenseItem incomeVsExpense = new AnalyticsDTO.IncomeVsExpenseItem(totalIncome, totalExpense);

        return new AnalyticsDTO(categoryBreakdown, monthlyTrend, incomeVsExpense);
    }
}
