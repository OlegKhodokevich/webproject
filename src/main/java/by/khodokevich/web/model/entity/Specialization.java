package by.khodokevich.web.model.entity;


import java.util.*;

/**
 * Specialization is entity outlining main types of builder specialization.
 *
 * @author Oleg Khodokevich
 *
 */
public enum Specialization {
    ELECTRICAL("specialization.electrical", "spec1"),
    PLUMBING("specialization.plumbing", "spec2"),
    PLASTERING("specialization.plastering", "spec3"),
    LAYING_TILES("specialization.laying_tiles", "spec4"),
    PAINTING("specialization.painting", "spec5"),
    WALLPAPERING("specialization.wallpapering", "spec6"),
    CEMENT_FLOOR("specialization.cement_floor", "spec7"),
    FLOOR_COVERING("specialization.floor_covering", "spec8"),
    CARPENTRY_WORK("specialization.carpentry_work", "spec9"),
    TURNKEY_HOUSE("specialization.turkey_house", "spec10"),
    ROOF("specialization.roof", "spec11"),
    MONOLITE("specialization.monolite", "spec12"),
    BRICKLAYING("specialization.bricklaying", "spec13"),
    FASAD("specialization.fasad", "spec14"),
    LANDSCAPING("specialization.landscaping", "spec15");

    private static final Map<String, Specialization> specializationMap = new HashMap<>();

    private static final List<Specialization> specializationList = new ArrayList<>();
    private final String key;
    private final String id;

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

        specializationList.add(ELECTRICAL);
        specializationList.add(PLUMBING);
        specializationList.add(PLASTERING);
        specializationList.add(LAYING_TILES);
        specializationList.add(PAINTING);
        specializationList.add(WALLPAPERING);
        specializationList.add(CEMENT_FLOOR);
        specializationList.add(FLOOR_COVERING);
        specializationList.add(CARPENTRY_WORK);
        specializationList.add(TURNKEY_HOUSE);
        specializationList.add(ROOF);
        specializationList.add(MONOLITE);
        specializationList.add(BRICKLAYING);
        specializationList.add(FASAD);
        specializationList.add(LANDSCAPING);
    }

    Specialization(String key, String id) {
        this.key = key;
        this.id = id;
    }

    public static Map<String, Specialization> getSpecializationMap() {
        return Collections.unmodifiableMap(specializationMap);
    }


    public static List<Specialization> getSpecializationList() {
        return Collections.unmodifiableList(specializationList);
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }

}
