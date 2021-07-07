package by.khodokevich.web.entity;

import java.util.Locale;

public class EntityMain {           //TODO
    public static void main(String[] args) {
        Locale localeEn = new Locale("en","US");
        System.out.println(Specialization.BRICKLAYING.getDescription(localeEn));
        System.out.println(Specialization.BRICKLAYING.getDescription());
        System.out.println(RegionBelarus.BREST_REGION.getName(localeEn));
        System.out.println(RegionBelarus.BREST_REGION.getName());
        System.out.println(RegionBelarus.MINSK_REGION.ordinal());

    }
}
