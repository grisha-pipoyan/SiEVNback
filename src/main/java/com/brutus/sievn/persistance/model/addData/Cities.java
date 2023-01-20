package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum Cities implements UriBasedEnum {

    Yerevan("Ереван","Yerevan","Երևան"),
    Gyumri("Гюмри","Gyumri","Գյումրի"),
    Tsaghkadzor("Цахкадзор","Tsaghkadzor","Ծաղկաձոր"),
    Dilijan("Дилиджан","Dilijan","Դիլիջան"),
    Ijevan("Иджеван","Ijevan","Իջևան"),
    Ejmiatsin("Эчмиадзин","Ejmiatsin","Էջմիածին"),
    Vanadzor("Ванадзор","Vanadzor","Վանաձոր"),
    Sevan("Севан","Sevan","Սևան"),
    Dzoraghbyur("Дзорахбюр","Dzoraghbyur","Ձորաղբյուր");

    private final String russian;
    private final String english;
    private final String armenian;


    private Cities(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }

    public static Cities forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (Cities) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static Cities forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (Cities) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static Cities forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (Cities) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, Cities> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, Cities> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, Cities> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, Cities> registerByReadableRU() {
            Map<String, Cities> map = new HashMap();
            Cities[] var1 = Cities.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Cities qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, Cities> registerByReadableEN() {
            Map<String, Cities> map = new HashMap();
            Cities[] var1 = Cities.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Cities qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, Cities> registerByReadableAM() {
            Map<String, Cities> map = new HashMap();
            Cities[] var1 = Cities.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Cities qualification = var1[var3];
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
