package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.RepairType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRepairTypeConverter implements Converter<String, RepairType> {

    @Override
    public RepairType convert(String source) {

        RepairType value = RepairType.forRussian(source);

        if (value == null) {

            value = RepairType.forEnglish(source);

            if (value == null) {
                value = RepairType.forArmenian(source);
            }
        }


        return value;
    }
}
