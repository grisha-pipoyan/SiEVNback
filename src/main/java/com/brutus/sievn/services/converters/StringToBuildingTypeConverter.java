package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.BuildingType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBuildingTypeConverter implements Converter<String, BuildingType> {
    @Override
    public BuildingType convert(String source) {

        BuildingType value = BuildingType.forRussian(source);

        if (value == null) {

            value = BuildingType.forEnglish(source);

            if (value == null) {
                value = BuildingType.forArmenian(source);
            }
        }

        return value;
    }
}
