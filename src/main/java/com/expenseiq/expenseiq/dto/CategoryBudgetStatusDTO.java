package com.expenseiq.expenseiq.dto;

import java.math.BigDecimal;

public class CategoryBudgetStatusDTO {

    private Long categoryId;
    private String categoryName;
    private String monthYear;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private BigDecimal remainingAmount;
    private boolean isExceeded;
    private double percentageUsed;

    public CategoryBudgetStatusDTO() {}

    public CategoryBudgetStatusDTO(Long categoryId, String categoryName, String monthYear, BigDecimal limitAmount, BigDecimal spentAmount, BigDecimal remainingAmount, boolean isExceeded, double percentageUsed) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.monthYear = monthYear;
        this.limitAmount = limitAmount;
        this.spentAmount = spentAmount;
        this.remainingAmount = remainingAmount;
        this.isExceeded = isExceeded;
        this.percentageUsed = percentageUsed;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public BigDecimal getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(BigDecimal limitAmount) {
        this.limitAmount = limitAmount;
    }

    public BigDecimal getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(BigDecimal spentAmount) {
        this.spentAmount = spentAmount;
    }

    public BigDecimal getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(BigDecimal remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public boolean isExceeded() {
        return isExceeded;
    }

    public void setExceeded(boolean exceeded) {
        isExceeded = exceeded;
    }

    public double getPercentageUsed() {
        return percentageUsed;
    }

    public void setPercentageUsed(double percentageUsed) {
        this.percentageUsed = percentageUsed;
    }
}
