package com.expenseiq.expenseiq.controller;

import com.expenseiq.expenseiq.dto.TransactionDTO;
import com.expenseiq.expenseiq.entity.TransactionType;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) TransactionType type) {
        return ResponseEntity.ok(transactionService.getTransactions(user, from, to, category, type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getTransactionById(id, user));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody TransactionDTO dto, @AuthenticationPrincipal User user) {
        TransactionDTO created = transactionService.createTransaction(dto, user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO dto, @AuthenticationPrincipal User user) {
        TransactionDTO updated = transactionService.updateTransaction(id, dto, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @AuthenticationPrincipal User user) {
        transactionService.deleteTransaction(id, user);
        return ResponseEntity.noContent().build();
    }
}
