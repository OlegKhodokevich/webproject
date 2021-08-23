package by.khodokevich.web.model.entity;


/**
 * OrderStatus outline state of order.
 *
 * @author Oleg Khodokevich
 *
 */
public enum OrderStatus {
    OPEN(3, "order.open"){
        @Override
        void working(Order order) {
            order.setStatus(IN_WORK);
        }
        @Override
        void close(Order order) {
            order.setStatus(CLOSE);
        }@Override
        void open(Order order) {
            order.setStatus(OPEN);
        }@Override
        void considering(Order order) {
            order.setStatus(UNDER_CONSIDERATION);
        }
    },
    CLOSE(4, "order.close"){
        @Override
        void working(Order order) {
            throw new UnsupportedOperationException();
        }
        @Override
        void close(Order order) {
            order.setStatus(CLOSE);
        }@Override
        void open(Order order) {
            order.setStatus(OPEN);
        }@Override
        void considering(Order order) {
            order.setStatus(UNDER_CONSIDERATION);
        }
    },
    UNDER_CONSIDERATION(1, "order.under_consideration"){
        @Override
        void working(Order order) {
            throw new UnsupportedOperationException();
        }
        @Override
        void close(Order order) {
            order.setStatus(CLOSE);
        }@Override
        void open(Order order) {
            order.setStatus(OPEN);
        }@Override
        void considering(Order order) {
            order.setStatus(UNDER_CONSIDERATION);
        }
    },
    IN_WORK(2, "order.in_work"){
        @Override
        void working(Order order) {
            order.setStatus(IN_WORK);
        }
        @Override
        void close(Order order) {
            throw new UnsupportedOperationException();
        }@Override
        void open(Order order) {
            order.setStatus(OPEN);
        }@Override
        void considering(Order order) {
            throw new UnsupportedOperationException();
        }
    };

    private final int priority;
    private final String key;

    OrderStatus(int priority, String key) {
        this.priority = priority;
        this.key = key;
    }

    public int getPriority() {
        return priority;
    }

    public String getKey() {
        return key;
    }

    abstract void working(Order order);

    abstract void close(Order order);

    abstract void open(Order order);

    abstract void considering(Order order);

    @Override
    public String toString() {
        return this.name();
    }
}
