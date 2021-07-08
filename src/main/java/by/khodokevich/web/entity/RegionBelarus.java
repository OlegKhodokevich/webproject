package by.khodokevich.web.entity;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public enum RegionBelarus {
    MINSK_REGION(1),
    HOMYEL_REGION(2),
    MAHILOU_REGION(3),
    VITEBSK_REGION(4),
    HRODNA_REGION(5),
    BREST_REGION(6);

    private int id;   //TODO if need

    RegionBelarus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
