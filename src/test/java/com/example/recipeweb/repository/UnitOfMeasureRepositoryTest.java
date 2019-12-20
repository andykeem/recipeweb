package com.example.recipeweb.repository;

import com.example.recipeweb.entity.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    private UnitOfMeasureRepository repo;

    @BeforeEach
    void setUp() {

    }

    @Test
//    @DirtiesContext
    public void checkOunceIsLoaded() {
        String val = "Ounce";
        Optional<UnitOfMeasure> uom = repo.findByValue(val);
        assertEquals(val, uom.get().getValue());
    }

    @Test
    public void checkDashIsLoaded() {
        String val = "Dash";
        Optional<UnitOfMeasure> uom = repo.findByValue(val);
        assertEquals(val, uom.get().getValue());
    }
}