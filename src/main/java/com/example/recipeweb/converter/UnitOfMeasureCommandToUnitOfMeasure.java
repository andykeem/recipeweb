package com.example.recipeweb.converter;

import com.example.recipeweb.command.UnitOfMeasureCommand;
import com.example.recipeweb.entity.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure target = new UnitOfMeasure();
        target.setId(source.getId());
        target.setValue(source.getValue());
        return target;
    }
}