package by.khodokevich.web.model.entity;

import java.util.*;

public enum RegionBelarus {


    MINSK_REGION(1, "region1"),
    HOMYEL_REGION(2, "region2"),
    MAHILOU_REGION(3, "region3"),
    VITEBSK_REGION(4, "region4"),
    HRODNA_REGION(5, "region5"),
    BREST_REGION(6, "region6");

    private static final List<RegionBelarus> regionList = new ArrayList<>();
    private static final Map<RegionBelarus, String> regionMap = new HashMap<>();

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

    RegionBelarus(int id, String key) {
        this.id = id;
        this.key = key;
    }


    public static List<RegionBelarus> getRegionList() {
//        List<RegionBelarus> transferList = new ArrayList<>(regionList.size());
//        for (int i = 0; i < regionList.size(); i++) {
//            transferList.add(regionList.get(i));
//        }
//        logger.debug(regionList.size() + "  " + transferList.size());
        return Collections.unmodifiableList(regionList);
    }

    public static Map<RegionBelarus, String> getRegionMap() {
        return Collections.unmodifiableMap(regionMap);
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
