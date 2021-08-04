package by.khodokevich.web.entity;

public enum OrderStatus {
    OPEN(2),
    CLOSE(3),
    UNDER_CONSIDERATION(1);

    private int priority;

    OrderStatus(int priority) {
        this.priority = priority;
    }



    public int getPriority(){
         return priority;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
