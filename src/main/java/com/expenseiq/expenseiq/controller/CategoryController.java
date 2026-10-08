package com.expenseiq.expenseiq.controller;

import com.expenseiq.expenseiq.dto.CategoryDTO;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(categoryService.getAllCategories(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(categoryService.getCategoryById(id, user));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto, @AuthenticationPrincipal User user) {
        CategoryDTO created = categoryService.createCategory(dto, user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto, @AuthenticationPrincipal User user) {
        CategoryDTO updated = categoryService.updateCategory(id, dto, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        categoryService.deleteCategory(id, user);
        return ResponseEntity.noContent().build();
    }
}
