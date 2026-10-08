package com.expenseiq.expenseiq.service;

import com.expenseiq.expenseiq.dto.TransactionDTO;
import com.expenseiq.expenseiq.entity.Category;
import com.expenseiq.expenseiq.entity.Transaction;
import com.expenseiq.expenseiq.entity.TransactionType;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.exception.ResourceNotFoundException;
import com.expenseiq.expenseiq.repository.CategoryRepository;
import com.expenseiq.expenseiq.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<TransactionDTO> getTransactions(User user, LocalDate from, LocalDate to, Long categoryId, TransactionType type) {
        return transactionRepository.filterTransactions(user.getId(), from, to, categoryId, type)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id, User user) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return convertToDTO(transaction);
    }

    public TransactionDTO createTransaction(TransactionDTO dto, User user) {
        Category category = categoryRepository.findByIdAndAvailableForUser(dto.getCategoryId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType() != null ? dto.getType() : category.getType());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());

        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO dto, User user) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        Category category = categoryRepository.findByIdAndAvailableForUser(dto.getCategoryId(), user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));

        transaction.setCategory(category);
        transaction.setAmount(dto.getAmount());
        transaction.setType(dto.getType() != null ? dto.getType() : category.getType());
        transaction.setDescription(dto.getDescription());
        transaction.setDate(dto.getDate());

        Transaction updated = transactionRepository.save(transaction);
        return convertToDTO(updated);
    }

    public void deleteTransaction(Long id, User user) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        transactionRepository.delete(transaction);
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDescription(),
                transaction.getDate()
        );
    }
}
