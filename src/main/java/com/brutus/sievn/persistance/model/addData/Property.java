package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum Property implements UriBasedEnum {

    Private("Частный дом","A private house","Սեփական տուն"),
    Apartment("Квартира","Apartment", "Բնակարան");

    private final String russian;
    private final String english;
    private final String armenian;


    private Property(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static Property forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (Property) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }
    public static Property forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (Property) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }
    public static Property forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (Property) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }



    private static class Registry {
        private static final Map<String, Property> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, Property> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, Property> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, Property> registerByReadableRU() {
            Map<String, Property> map = new HashMap();
            Property[] var1 = Property.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Property qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, Property> registerByReadableEN() {
            Map<String, Property> map = new HashMap();
            Property[] var1 = Property.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Property qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, Property> registerByReadableAM() {
            Map<String, Property> map = new HashMap();
            Property[] var1 = Property.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Property qualification = var1[var3];
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
