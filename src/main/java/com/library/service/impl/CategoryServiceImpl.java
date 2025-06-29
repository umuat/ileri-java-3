package com.library.service.impl;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import com.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Kategori servis implementasyonu
 * 
 * Bu sınıf CategoryService interface'ini implement eder.
 */
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, Long> implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findMostPopularCategories(int limit) {
        return categoryRepository.findMostPopularCategories(limit);
    }

    @Override
    public List<Category> findEmptyCategories() {
        return categoryRepository.findEmptyCategories();
    }

    @Override
    public List<Category> findByColorCode(String colorCode) {
        return categoryRepository.findByColorCode(colorCode);
    }
} 