package by.khodokevich.web.entity;

import by.khodokevich.web.command.impl.GoToRegistrationCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public enum RegionBelarus {


    MINSK_REGION(1, "region1"),
    HOMYEL_REGION(2, "region2"),
    MAHILOU_REGION(3, "region3"),
    VITEBSK_REGION(4, "region4"),
    HRODNA_REGION(5, "region5"),
    BREST_REGION(6, "region6");

    private static final Logger logger = LogManager.getLogger(RegionBelarus.class);

    private static List<RegionBelarus> regions = new ArrayList<>();

    private int id;
    private String key;

    static {
        regions.add(MINSK_REGION);
        regions.add(HOMYEL_REGION);
        regions.add(MAHILOU_REGION);
        regions.add(VITEBSK_REGION);
        regions.add(HRODNA_REGION);
        regions.add(BREST_REGION);
    }

    RegionBelarus(int id, String key) {
        this.id = id;
        this.key = key;
    }


    public static List<RegionBelarus> getRegions() {
        List<RegionBelarus> transferList = new ArrayList<>(regions.size());
        for (int i = 0; i < regions.size(); i++) {
            transferList.add(regions.get(i));
        }
        logger.debug(regions.size() + "  " + transferList.size());
        return transferList;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }
}
