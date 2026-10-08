package com.expenseiq.expenseiq.service;

import com.expenseiq.expenseiq.dto.CategoryDTO;
import com.expenseiq.expenseiq.entity.Category;
import com.expenseiq.expenseiq.entity.User;
import com.expenseiq.expenseiq.exception.BadRequestException;
import com.expenseiq.expenseiq.exception.ConflictException;
import com.expenseiq.expenseiq.exception.ResourceNotFoundException;
import com.expenseiq.expenseiq.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories(User user) {
        return categoryRepository.findAllAvailableForUser(user.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id, User user) {
        Category category = categoryRepository.findByIdAndAvailableForUser(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return convertToDTO(category);
    }

    public CategoryDTO createCategory(CategoryDTO dto, User user) {
        if (categoryRepository.existsByNameForUser(dto.getName().trim(), user.getId())) {
            throw new ConflictException("Category with name '" + dto.getName() + "' already exists");
        }

        Category category = new Category();
        category.setName(dto.getName().trim());
        category.setType(dto.getType());
        category.setUser(user);

        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO dto, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (category.getUser() == null) {
            throw new BadRequestException("Default system categories cannot be modified");
        }

        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        category.setName(dto.getName().trim());
        category.setType(dto.getType());

        Category updated = categoryRepository.save(category);
        return convertToDTO(updated);
    }

    public void deleteCategory(Long id, User user) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (category.getUser() == null) {
            throw new BadRequestException("Default system categories cannot be deleted");
        }

        if (!category.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }

        categoryRepository.delete(category);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.getUser() == null
        );
    }
}
