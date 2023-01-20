package com.brutus.sievn.services.converters;

import com.brutus.sievn.persistance.model.addData.PaymentMethod;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPaymentMethod implements Converter<String, PaymentMethod> {

    @Override
    public PaymentMethod convert(String source) {

        PaymentMethod value = PaymentMethod.forRussian(source);

        if (value == null) {

            value = PaymentMethod.forEnglish(source);

            if (value == null) {
                value = PaymentMethod.forArmenian(source);
            }
        }

        return value;
    }

}
