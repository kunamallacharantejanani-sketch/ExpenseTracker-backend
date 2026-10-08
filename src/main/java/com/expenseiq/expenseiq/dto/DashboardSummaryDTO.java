package com.expenseiq.expenseiq.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardSummaryDTO {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal balance;
    private BigDecimal currentMonthSpend;
    private List<CategoryBudgetStatusDTO> budgetStatusList;

    public DashboardSummaryDTO() {}

    public DashboardSummaryDTO(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal balance, BigDecimal currentMonthSpend, List<CategoryBudgetStatusDTO> budgetStatusList) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.balance = balance;
        this.currentMonthSpend = currentMonthSpend;
        this.budgetStatusList = budgetStatusList;
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getCurrentMonthSpend() {
        return currentMonthSpend;
    }

    public void setCurrentMonthSpend(BigDecimal currentMonthSpend) {
        this.currentMonthSpend = currentMonthSpend;
    }

    public List<CategoryBudgetStatusDTO> getBudgetStatusList() {
        return budgetStatusList;
    }

    public void setBudgetStatusList(List<CategoryBudgetStatusDTO> budgetStatusList) {
        this.budgetStatusList = budgetStatusList;
    }
}
