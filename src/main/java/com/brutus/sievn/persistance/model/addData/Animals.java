package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum Animals implements UriBasedEnum {

    WithAnimals("С животными","With animals","Կենդանիներով"),
    WithoutAnimals("Без животных","Without animals", "Առանց կենդանիների");


    private final String russian;
    private final String english;
    private final String armenian;


    private Animals(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static Animals forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (Animals) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }
    public static Animals forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (Animals) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }
    public static Animals forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (Animals) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }



    private static class Registry {
        private static final Map<String, Animals> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, Animals> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, Animals> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, Animals> registerByReadableRU() {
            Map<String, Animals> map = new HashMap();
            Animals[] var1 = Animals.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Animals qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, Animals> registerByReadableEN() {
            Map<String, Animals> map = new HashMap();
            Animals[] var1 = Animals.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Animals qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, Animals> registerByReadableAM() {
            Map<String, Animals> map = new HashMap();
            Animals[] var1 = Animals.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Animals qualification = var1[var3];
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
