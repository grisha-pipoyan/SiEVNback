package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum Balcony implements UriBasedEnum {

    WithBalcony("С балконом","With balcony","Պատշգամբով"),
    WithoutBalcony("Без балкона","Without balcony","Առանց պատշգամբի");

    private final String russian;
    private final String english;
    private final String armenian;


    private Balcony(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }




    public static Balcony forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (Balcony)
                Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }
    public static Balcony forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (Balcony)
                Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static Balcony forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (Balcony)
                Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, Balcony> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, Balcony> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, Balcony> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, Balcony> registerByReadableRU() {
            Map<String, Balcony> map = new HashMap();
            Balcony[] var1 = Balcony.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Balcony qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, Balcony> registerByReadableEN() {
            Map<String, Balcony> map = new HashMap();
            Balcony[] var1 = Balcony.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Balcony qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, Balcony> registerByReadableAM() {
            Map<String, Balcony> map = new HashMap();
            Balcony[] var1 = Balcony.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Balcony qualification = var1[var3];
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
