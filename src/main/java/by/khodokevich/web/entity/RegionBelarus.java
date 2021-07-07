package by.khodokevich.web.entity;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum RegionBelarus {
    MINSK_REGION(1, "region1"),
    HOMYEL_REGION(2, "region2"),
    MAHILOU_REGION(3, "region3"),
    VITEBSK_REGION(4, "region4"),
    HRODNA_REGION(5, "region5"),
    BREST_REGION(6, "region6");

    private int id;
    private String key;


    RegionBelarus(int id, String key) {
        this.key = key;
        this.id = id;
    }

    public String getName(Locale locale) {
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(Constant.PROPERTIES_FILE_NAME, locale);
        } catch (MissingResourceException e) {
            throw new RuntimeException(e);                   //TODO
        }
        return resourceBundle.getString(key);
    }

    public String getName() {
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(Constant.PROPERTIES_FILE_NAME);
        } catch (MissingResourceException e) {
            throw new RuntimeException(e);                   //TODO
        }
        return resourceBundle.getString(key);
    }

    public int getId() {
        return id;
    }
}
