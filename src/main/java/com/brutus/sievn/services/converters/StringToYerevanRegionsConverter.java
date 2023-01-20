package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.YerevanRegions;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToYerevanRegionsConverter implements Converter<String, YerevanRegions> {
    @Override
    public YerevanRegions convert(String source) {

        YerevanRegions value = YerevanRegions.forRussian(source);

        if (value == null) {

            value = YerevanRegions.forEnglish(source);

            if (value == null) {
                value = YerevanRegions.forArmenian(source);
            }
        }

        return value;


    }
}
