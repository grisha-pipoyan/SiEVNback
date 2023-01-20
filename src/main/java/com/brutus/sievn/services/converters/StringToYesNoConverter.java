package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.YesNo;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToYesNoConverter implements Converter<String, YesNo> {
    @Override
    public YesNo convert(String source) {

        YesNo value = YesNo.forRussian(source);

        if (value == null) {

            value = YesNo.forEnglish(source);

            if (value == null) {
                value = YesNo.forArmenian(source);
            }
        }

        return value;
    }
}
