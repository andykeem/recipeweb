package com.example.recipeweb.repository;

import com.example.recipeweb.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository catRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void test_findByName() {
        String name = "Mexican";
        Optional<Category> data = catRepo.findByName(name);
        assertEquals(name, data.get().getName());
    }

    @Test
    public void test_One() {
        Category cat = new Category("Italian");
        Category newCat = catRepo.save(cat);
        assertEquals(cat, newCat);
    }
}