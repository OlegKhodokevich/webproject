package by.khodokevich.web.entity;

import java.util.*;

public enum Specialization {
    ELECTRICAL("specialization1"),
    PLUMBING("specialization2"),
    PLASTERING("specialization3"),
    LAYING_TILES("specialization4"),
    PAINTING("specialization5"),
    WALLPAPERING("specialization7"),
    CEMENT_FLOOR("specialization10"),
    FLOOR_COVERING("specialization11"),
    CARPENTRY_WORK("specialization14"),
    TURNKEY_HOUSE("specialization16"),
    ROOF("specialization18"),
    MONOLITE("specialization19"),
    BRICKLAYING("specialization20"),
    FASAD("specialization22"),
    LANDSCAPING("specialization24");

    private String key;
    private static final Map<String, Specialization> specializationMap = new HashMap<>();

    static {
        specializationMap.put("spec1", ELECTRICAL);
        specializationMap.put("spec2", PLUMBING);
        specializationMap.put("spec3", PLASTERING);


    }

    Specialization(String key) {
        this.key = key;
    }

    public String getDescription(Locale locale) {
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(Constant.PROPERTIES_FILE_NAME, locale);
        } catch (MissingResourceException e) {
            throw new RuntimeException(e);              //TODO
        }

        String description = resourceBundle.getString(key);
        return description;
    }

    public String getDescription() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(Constant.PROPERTIES_FILE_NAME);
        String description = resourceBundle.getString(key);
        return description;
    }


    public static Map<String, Specialization> getSpecializationMap() {
        Map<String, Specialization> destinationMap = new HashMap<>();
        destinationMap.putAll(specializationMap);
        return specializationMap;
    }
}
