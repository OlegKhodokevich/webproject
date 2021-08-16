package by.khodokevich.web.model.entity;

import java.util.*;

/**
 * Region Belarus is present list of Belarus region.
 *
 * @author Oleg Khodokevich
 *
 */
public enum Region {
    MINSK_REGION(1, "region1"),
    HOMYEL_REGION(2, "region2"),
    MAHILOU_REGION(3, "region3"),
    VITEBSK_REGION(4, "region4"),
    HRODNA_REGION(5, "region5"),
    BREST_REGION(6, "region6");

    private static final List<Region> regionList = new ArrayList<>();
    private static final Map<Region, String> regionMap = new HashMap<>();

    private final int id;
    private final String key;

    static {
        regionList.add(MINSK_REGION);
        regionList.add(HOMYEL_REGION);
        regionList.add(MAHILOU_REGION);
        regionList.add(VITEBSK_REGION);
        regionList.add(HRODNA_REGION);
        regionList.add(BREST_REGION);

        regionMap.put(MINSK_REGION, MINSK_REGION.key);
        regionMap.put(HOMYEL_REGION, HOMYEL_REGION.key);
        regionMap.put(MAHILOU_REGION, MAHILOU_REGION.key);
        regionMap.put(VITEBSK_REGION, VITEBSK_REGION.key);
        regionMap.put(HRODNA_REGION, HRODNA_REGION.key);
        regionMap.put(BREST_REGION, BREST_REGION.key);
    }

    Region(int id, String key) {
        this.id = id;
        this.key = key;
    }

    public static List<Region> getRegionList() {
        return Collections.unmodifiableList(regionList);
    }

    public static Map<Region, String> getRegionMap() {
        return Collections.unmodifiableMap(regionMap);
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
