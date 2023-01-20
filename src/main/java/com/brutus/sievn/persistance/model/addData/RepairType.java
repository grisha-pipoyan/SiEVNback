package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum RepairType implements UriBasedEnum {

    None("Без ремонта","None","Առանց"),
    Old("Старый","Old","Հին"),
    Partial("Частичный","Partial","Մասնակի"),
    Cosmetic("Косметический","Cosmetic","Կոսմետիկ"),
    Euro("Евроремонт","Euro","Եվրո"),
    Design("Дизайнерский","Design","Դիզայներական"),
    Capital("Капитальный","Capital","Կապիտալ");


    private final String russian;
    private final String english;
    private final String armenian;


    private RepairType(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static RepairType forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (RepairType) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static RepairType forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (RepairType) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static RepairType forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (RepairType) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, RepairType> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, RepairType> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, RepairType> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, RepairType> registerByReadableRU() {
            Map<String, RepairType> map = new HashMap();
            RepairType[] var1 = RepairType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                RepairType qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, RepairType> registerByReadableEN() {
            Map<String, RepairType> map = new HashMap();
            RepairType[] var1 = RepairType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                RepairType qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, RepairType> registerByReadableAM() {
            Map<String, RepairType> map = new HashMap();
            RepairType[] var1 = RepairType.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                RepairType qualification = var1[var3];
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
