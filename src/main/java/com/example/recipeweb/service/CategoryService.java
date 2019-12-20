package com.example.recipeweb.service;

import com.example.recipeweb.entity.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> findAll();
}
