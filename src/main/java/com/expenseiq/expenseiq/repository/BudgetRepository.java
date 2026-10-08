package com.expenseiq.expenseiq.repository;

import com.expenseiq.expenseiq.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    List<Budget> findByUserIdAndMonthYear(Long userId, String monthYear);

    List<Budget> findByUserId(Long userId);

    Optional<Budget> findByUserIdAndCategoryIdAndMonthYear(Long userId, Long categoryId, String monthYear);

    Optional<Budget> findByIdAndUserId(Long id, Long userId);
}
