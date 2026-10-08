package com.expenseiq.expenseiq.repository;

import com.expenseiq.expenseiq.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.user IS NULL OR c.user.id = :userId ORDER BY c.id ASC")
    List<Category> findAllAvailableForUser(@Param("userId") Long userId);

    @Query("SELECT c FROM Category c WHERE c.id = :id AND (c.user IS NULL OR c.user.id = :userId)")
    Optional<Category> findByIdAndAvailableForUser(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) = LOWER(:name) AND (c.user IS NULL OR c.user.id = :userId)")
    Optional<Category> findByNameAndUserOrDefault(@Param("name") String name, @Param("userId") Long userId);

    boolean existsByNameIgnoreCaseAndUserIsNull(String name);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE LOWER(c.name) = LOWER(:name) AND (c.user.id = :userId OR c.user IS NULL)")
    boolean existsByNameForUser(@Param("name") String name, @Param("userId") Long userId);
}
