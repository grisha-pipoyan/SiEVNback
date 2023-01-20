package com.brutus.sievn.persistance.model.addData;

import eu.europa.esig.dss.enumerations.UriBasedEnum;

import java.util.HashMap;
import java.util.Map;

public enum YerevanRegions implements UriBasedEnum {

    Ajapnyak("Аджапняк","Ajapnyak","Աջափնյակ"),
    Arabkir("Арабкир","Arabkir","Արաբկիր"),
    Avan("Аван","Avan","Ավան"),
    Davtashen("Давташен","Davtashen","Դավթաշեն"),
    Erebuni("Эребуни","Erebuni","Էրեբունի"),
    Kanaker_Zeytun("Канакер-Зейтун","Kanaker-Zeytun","Քանաքեռ-Զեյթուն"),
    Kentron("Кентрон","Kentron","Կենտրոն"),
    Malatia_Sebastya("Малатия-Себастия","Malatia-Sebastya","Մալաթիա-Սեբաստիա"),
    Nork_Marash("Норк-Мараш","Nork-Marash","Նորք-Մարաշ"),
    Nor_Nork("Нор Норк","Nor Nork","Նոր Նորք"),
    Nubarashen("Нубарашен","Nubarashen","Նուբարաշեն"),
    Shengavit("Шенгавит","Shengavit","Շենգավիթ");

    private final String russian;
    private final String english;
    private final String armenian;


    private YerevanRegions(String russian, String english, String armenian) {
        this.russian = russian;
        this.english = english;
        this.armenian = armenian;
    }


    public static YerevanRegions forRussian(String readable) {
        return readable != null && !readable.isEmpty() ? (YerevanRegions) Registry.QUALIFS_BY_READABLE_RU.get(readable) : null;
    }

    public static YerevanRegions forEnglish(String readable) {
        return readable != null && !readable.isEmpty() ? (YerevanRegions) Registry.QUALIFS_BY_READABLE_EN.get(readable) : null;
    }

    public static YerevanRegions forArmenian(String readable) {
        return readable != null && !readable.isEmpty() ? (YerevanRegions) Registry.QUALIFS_BY_READABLE_AM.get(readable) : null;
    }


    private static class Registry {
        private static final Map<String, YerevanRegions> QUALIFS_BY_READABLE_RU = registerByReadableRU();
        private static final Map<String, YerevanRegions> QUALIFS_BY_READABLE_EN = registerByReadableEN();
        private static final Map<String, YerevanRegions> QUALIFS_BY_READABLE_AM = registerByReadableAM();

        private Registry() {
        }

        private static Map<String, YerevanRegions> registerByReadableRU() {
            Map<String, YerevanRegions> map = new HashMap();
            YerevanRegions[] var1 = YerevanRegions.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YerevanRegions qualification = var1[var3];
                map.put(qualification.russian, qualification);
            }

            return map;
        }

        private static Map<String, YerevanRegions> registerByReadableEN() {
            Map<String, YerevanRegions> map = new HashMap();
            YerevanRegions[] var1 = YerevanRegions.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YerevanRegions qualification = var1[var3];
                map.put(qualification.english, qualification);
            }

            return map;
        }

        private static Map<String, YerevanRegions> registerByReadableAM() {
            Map<String, YerevanRegions> map = new HashMap();
            YerevanRegions[] var1 = YerevanRegions.values();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                YerevanRegions qualification = var1[var3];
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
