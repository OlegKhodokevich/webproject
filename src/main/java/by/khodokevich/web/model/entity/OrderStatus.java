package by.khodokevich.web.model.entity;

public enum OrderStatus {
    OPEN(2, "order.open"),
    CLOSE(3, "order.close"),
    UNDER_CONSIDERATION(1, "order.under_consideration"),
    IN_WORK(4, "order.in_work");

    private final int priority;
    private  final String key;

    OrderStatus(int priority, String key) {
        this.priority = priority;
        this.key = key;
    }

    public int getPriority(){
         return priority;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return this.name();
    }
}