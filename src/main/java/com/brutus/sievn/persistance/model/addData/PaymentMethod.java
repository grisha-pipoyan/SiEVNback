package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum PaymentMethod implements UriBasedEnum {

    Monthly("Ежемесячная","Monthly","Ամսեկան"),
    Daily("Посуточная","Daily","Օրական"),
    Sale("Продажа","Sale","Վաճառք"),
    Commercial("Коммерческая","Commercial","Կոմերցիոն");


    private final String russian;
    private final String english;
    private final String armenian;


    private PaymentMethod(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }

    public static PaymentMethod forRussian(String readable) {
        return readable != null && !readable.isEmpty() ?
                (PaymentMethod) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static PaymentMethod forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ?
                (PaymentMethod) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static PaymentMethod forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ?
                (PaymentMethod) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, PaymentMethod> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, PaymentMethod> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, PaymentMethod> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, PaymentMethod> registerByReadableRU() {
            Map<String, PaymentMethod> map = new HashMap();
            PaymentMethod[] var1 = PaymentMethod.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PaymentMethod qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, PaymentMethod> registerByReadableEN() {
            Map<String, PaymentMethod> map = new HashMap();
            PaymentMethod[] var1 = PaymentMethod.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PaymentMethod qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, PaymentMethod> registerByReadableAM() {
            Map<String, PaymentMethod> map = new HashMap();
            PaymentMethod[] var1 = PaymentMethod.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                PaymentMethod qualification = var1[var3];
                map.put(qualification.armenian, qualification);
            }

            return map;
        }
    }

    public String getRussian() {
        return this.russian;
    }

    public String getEnglish() {
        return this.english;
    }

    public String getUri() {
        return this.armenian;
    }


}
