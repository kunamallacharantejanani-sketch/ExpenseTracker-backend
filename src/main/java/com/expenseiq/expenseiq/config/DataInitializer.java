package com.expenseiq.expenseiq.config;

import com.expenseiq.expenseiq.entity.Category;
import com.expenseiq.expenseiq.entity.TransactionType;
import com.expenseiq.expenseiq.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed default categories if none exist
        if (categoryRepository.findAll().stream().noneMatch(c -> c.getUser() == null)) {
            List<Category> defaultCategories = Arrays.asList(
                // Expense Categories
                new Category(null, "Food & Dining", TransactionType.EXPENSE, null),
                new Category(null, "Rent & Housing", TransactionType.EXPENSE, null),
                new Category(null, "Travel & Transport", TransactionType.EXPENSE, null),
                new Category(null, "Utilities & Bills", TransactionType.EXPENSE, null),
                new Category(null, "Entertainment", TransactionType.EXPENSE, null),
                new Category(null, "Shopping", TransactionType.EXPENSE, null),
                new Category(null, "Healthcare & Medical", TransactionType.EXPENSE, null),
                new Category(null, "Education", TransactionType.EXPENSE, null),
                // Income Categories
                new Category(null, "Salary", TransactionType.INCOME, null),
                new Category(null, "Freelance & Consulting", TransactionType.INCOME, null),
                new Category(null, "Investment & Dividends", TransactionType.INCOME, null),
                new Category(null, "Gifts & Allowance", TransactionType.INCOME, null)
            );

            categoryRepository.saveAll(defaultCategories);
            System.out.println("Default categories initialized successfully!");
        }
    }
}
