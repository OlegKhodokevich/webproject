package by.khodokevich.web.entity;


import java.util.*;

public enum Specialization {
    ELECTRICAL,
    PLUMBING,
    PLASTERING,
    LAYING_TILES,
    PAINTING,
    WALLPAPERING,
    CEMENT_FLOOR,
    FLOOR_COVERING,
    CARPENTRY_WORK,
    TURNKEY_HOUSE,
    ROOF,
    MONOLITE,
    BRICKLAYING,
    FASAD,
    LANDSCAPING;

    private static final Map<String, Specialization> specializationMap = new HashMap<>();

    static {
        specializationMap.put("spec1", ELECTRICAL);
        specializationMap.put("spec2", PLUMBING);
        specializationMap.put("spec3", PLASTERING);
        specializationMap.put("spec4", LAYING_TILES);
        specializationMap.put("spec5", PAINTING);
        specializationMap.put("spec6", WALLPAPERING);
        specializationMap.put("spec7", CEMENT_FLOOR);
        specializationMap.put("spec8", FLOOR_COVERING);
        specializationMap.put("spec9", CARPENTRY_WORK);
        specializationMap.put("spec10", TURNKEY_HOUSE);
        specializationMap.put("spec11", ROOF);
        specializationMap.put("spec12", MONOLITE);
        specializationMap.put("spec13", BRICKLAYING);
        specializationMap.put("spec14", FASAD);
        specializationMap.put("spec15", LANDSCAPING);


    }

    public static Map<String, Specialization> getSpecializationMap() {
        Map<String, Specialization> destinationMap = new HashMap<>();
        destinationMap.putAll(specializationMap);
        return specializationMap;
    }
}
