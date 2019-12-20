package com.example.recipeweb.service;

import com.example.recipeweb.entity.Category;
import com.example.recipeweb.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Set<Category> findAll() {
        Set<Category> set = new HashSet<>();
        categoryRepo.findAll().iterator().forEachRemaining(set::add);
        return set;
    }
}
