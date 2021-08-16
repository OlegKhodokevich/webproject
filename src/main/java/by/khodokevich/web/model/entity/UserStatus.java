package by.khodokevich.web.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UserStatus is state of user.
 *
 * @author Oleg Khodokevich
 *
 */
public enum UserStatus {
    DECLARED,
    CONFIRMED,
    ARCHIVED;

    private static final List<UserStatus> statusList;

    static {
        statusList = new ArrayList<>();
        statusList.add(DECLARED);
        statusList.add(CONFIRMED);
        statusList.add(ARCHIVED);
    }

    public static List<UserStatus> getStatusList() {
        return Collections.unmodifiableList(statusList);
    }

}
