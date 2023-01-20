package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.Animals;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToAnimalsConverter implements Converter<String, Animals> {
    @Override
    public Animals convert(String source) {

        Animals value = Animals.forRussian(source);

        if (value == null) {

            value = Animals.forEnglish(source);

            if (value == null) {
                value = Animals.forArmenian(source);
            }
        }

        return value;
    }
}
