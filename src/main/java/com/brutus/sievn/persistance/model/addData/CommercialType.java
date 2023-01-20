package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum CommercialType implements UriBasedEnum {

    Office("Офисное помещение","Office","Օֆիսային տարածք"),
    Premise("Торговое помещение","Commercial premises","Առևտրային տարածք"),
    Business("Бизнес","Business","Բիզնես"),
    Other("Другое","Other","Այլ");

    private final String russian;
    private final String english;
    private final String armenian;


    private CommercialType(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static CommercialType forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (CommercialType) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static CommercialType forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (CommercialType) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static CommercialType forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (CommercialType) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }



    private static class Registry {
        private static final Map<String, CommercialType> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, CommercialType> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, CommercialType> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, CommercialType> registerByReadableRU() {
            Map<String, CommercialType> map = new HashMap();
            CommercialType[] var1 = CommercialType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CommercialType qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, CommercialType> registerByReadableEN() {
            Map<String, CommercialType> map = new HashMap();
            CommercialType[] var1 = CommercialType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CommercialType qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, CommercialType> registerByReadableAM() {
            Map<String, CommercialType> map = new HashMap();
            CommercialType[] var1 = CommercialType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                CommercialType qualification = var1[var3];
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
