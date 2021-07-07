package by.khodokevich.web.util;

public class IdGenerator {
    private static int userCounter = 0;

    public static int incrementUserId() {
        return ++userCounter;
    }
}
