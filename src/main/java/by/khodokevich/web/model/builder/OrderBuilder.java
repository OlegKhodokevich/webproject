package by.khodokevich.web.model.builder;

import by.khodokevich.web.model.entity.Order;
import by.khodokevich.web.model.entity.OrderStatus;
import by.khodokevich.web.model.entity.Specialization;

import java.util.Date;

public class OrderBuilder {
    private long orderId;
    private long userId;
    private String title;
    private String description;
    private String address;
    private Date creationDate;
    private Date completionDate;
    private Specialization specialization;
    private OrderStatus status;

    public OrderBuilder() {
    }

    public OrderBuilder orderId(long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public OrderBuilder title(String title) {
        this.title = title;
        return this;
    }

    public OrderBuilder description(String description) {
        this.description = description;
        return this;
    }

    public OrderBuilder address(String address) {
        this.address = address;
        return this;
    }

    public OrderBuilder creationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public OrderBuilder completionDate(Date completionDate) {
        this.completionDate = completionDate;
        return this;
    }

    public OrderBuilder specialization(Specialization specialization) {
        this.specialization = specialization;
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Order buildOrderWithoutId() {
        return new Order(userId, title, description, address, creationDate, completionDate, specialization, status);
    }

    public Order buildOrder() {
        return new Order(orderId, userId, title, description, address, creationDate, completionDate, specialization, status);
    }
}
