package com.example.recipeweb.bootstrap;

import com.example.recipeweb.entity.Category;
import com.example.recipeweb.entity.UnitOfMeasure;
import com.example.recipeweb.repository.CategoryRepository;
import com.example.recipeweb.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
@Profile({"dev", "prod"})
public class BootstrapMySQL implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository catRepo;
    private final UnitOfMeasureRepository uomRepo;

    public BootstrapMySQL(CategoryRepository catRepo, UnitOfMeasureRepository uomRepo) {
        this.catRepo = catRepo;
        this.uomRepo = uomRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        init();
    }

    private void init() {
        try {
            if (catRepo.count() == 0) {
                loadCategories();
            }
            if (uomRepo.count() == 0) {
                loadUnitOfMeasures();
            }
        } catch (SQLGrammarException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void loadCategories() {
        List<String> items = Arrays.asList("American", "Italian", "Mexican", "Fast Food");
        items.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                catRepo.save(new Category(s));
            }
        });
    }

    private void loadUnitOfMeasures() {
        List<String> items = Arrays.asList("Teaspoon", "Tablespoon", "Cup", "Pinch", "Ounce", "Dash", "Ripe");
        items.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                uomRepo.save(new UnitOfMeasure(s));
            }
        });
    }
}
