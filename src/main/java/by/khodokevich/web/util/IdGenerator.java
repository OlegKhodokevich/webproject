package by.khodokevich.web.util;

public class IdGenerator {  //TODO if need
    private static int userCounter = 0;

    public static int incrementUserId() {
        return ++userCounter;
    }
}
