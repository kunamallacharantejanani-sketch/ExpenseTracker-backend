package com.expenseiq.expenseiq.dto;

import java.math.BigDecimal;
import java.util.List;

public class AnalyticsDTO {

    private List<CategoryExpenseItem> categoryBreakdown;
    private List<MonthlyTrendItem> monthlyTrend;
    private IncomeVsExpenseItem incomeVsExpense;

    public AnalyticsDTO() {}

    public AnalyticsDTO(List<CategoryExpenseItem> categoryBreakdown, List<MonthlyTrendItem> monthlyTrend, IncomeVsExpenseItem incomeVsExpense) {
        this.categoryBreakdown = categoryBreakdown;
        this.monthlyTrend = monthlyTrend;
        this.incomeVsExpense = incomeVsExpense;
    }

    public List<CategoryExpenseItem> getCategoryBreakdown() {
        return categoryBreakdown;
    }

    public void setCategoryBreakdown(List<CategoryExpenseItem> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
    }

    public List<MonthlyTrendItem> getMonthlyTrend() {
        return monthlyTrend;
    }

    public void setMonthlyTrend(List<MonthlyTrendItem> monthlyTrend) {
        this.monthlyTrend = monthlyTrend;
    }

    public IncomeVsExpenseItem getIncomeVsExpense() {
        return incomeVsExpense;
    }

    public void setIncomeVsExpense(IncomeVsExpenseItem incomeVsExpense) {
        this.incomeVsExpense = incomeVsExpense;
    }

    public static class CategoryExpenseItem {
        private String categoryName;
        private BigDecimal amount;

        public CategoryExpenseItem() {}

        public CategoryExpenseItem(String categoryName, BigDecimal amount) {
            this.categoryName = categoryName;
            this.amount = amount;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

    public static class MonthlyTrendItem {
        private String monthYear; // e.g., "2026-09"
        private BigDecimal income;
        private BigDecimal expense;

        public MonthlyTrendItem() {}

        public MonthlyTrendItem(String monthYear, BigDecimal income, BigDecimal expense) {
            this.monthYear = monthYear;
            this.income = income;
            this.expense = expense;
        }

        public String getMonthYear() {
            return monthYear;
        }

        public void setMonthYear(String monthYear) {
            this.monthYear = monthYear;
        }

        public BigDecimal getIncome() {
            return income;
        }

        public void setIncome(BigDecimal income) {
            this.income = income;
        }

        public BigDecimal getExpense() {
            return expense;
        }

        public void setExpense(BigDecimal expense) {
            this.expense = expense;
        }
    }

    public static class IncomeVsExpenseItem {
        private BigDecimal totalIncome;
        private BigDecimal totalExpense;

        public IncomeVsExpenseItem() {}

        public IncomeVsExpenseItem(BigDecimal totalIncome, BigDecimal totalExpense) {
            this.totalIncome = totalIncome;
            this.totalExpense = totalExpense;
        }

        public BigDecimal getTotalIncome() {
            return totalIncome;
        }

        public void setTotalIncome(BigDecimal totalIncome) {
            this.totalIncome = totalIncome;
        }

        public BigDecimal getTotalExpense() {
            return totalExpense;
        }

        public void setTotalExpense(BigDecimal totalExpense) {
            this.totalExpense = totalExpense;
        }
    }
}
