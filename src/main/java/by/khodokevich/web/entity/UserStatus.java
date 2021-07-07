package by.khodokevich.web.entity;

public enum UserStatus {
    DECLARED("Declared"),
    CONFIRMED("Confirmed"),
    ARCHIVED("Archived");

    private String nameStatus;

    UserStatus(String nameStatus) {
        this.nameStatus = nameStatus;
    }
}
