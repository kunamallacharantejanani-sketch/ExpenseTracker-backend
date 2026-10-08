package com.expenseiq.expenseiq.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public class BudgetDTO {

    private Long id;

    @NotNull(message = "Category is required")
    private Long categoryId;

    private String categoryName;

    @NotBlank(message = "Month and year are required")
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "Month-Year must be in format YYYY-MM")
    private String monthYear;

    @NotNull(message = "Limit amount is required")
    @DecimalMin(value = "0.01", message = "Limit amount must be greater than 0")
    private BigDecimal limitAmount;

    public BudgetDTO() {}

    public BudgetDTO(Long id, Long categoryId, String categoryName, String monthYear, BigDecimal limitAmount) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.monthYear = monthYear;
        this.limitAmount = limitAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
