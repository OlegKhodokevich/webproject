package by.khodokevich.web.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order extends Entity {
    private long orderId;
    private long userId;
    private String title;
    private String description;
    private String address;
    private Date creationDate;
    private Date completionDate;
    private Specialization specialization;
    private OrderStatus status;

    public Order() {
    }

    public Order(long userId, String title, String description, String address, Date creationDate, Date completionDate, Specialization specialization, OrderStatus status) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.address = address;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.specialization = specialization;
        this.status = status;
    }

    public Order(long orderId, long userId, String title, String description, String address, Date creationDate, Date completionDate, Specialization specialization, OrderStatus status) {
        this.orderId = orderId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.address = address;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.specialization = specialization;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && userId == order.userId && title.equals(order.title) && description.equals(order.description) && address.equals(order.address) && creationDate.equals(order.creationDate) && completionDate.equals(order.completionDate) && specialization == order.specialization && status == order.status;
    }

    @Override
    public int hashCode() {
        int result = (int) orderId + 31 * title.hashCode();
        result = result * 31 + (int) userId;
        result = result * 31 + description.hashCode();
        result = result * 31 + address.hashCode();
        result = result * 31 + creationDate.hashCode();
        result = result * 31 + completionDate.hashCode();
        result = result * 31 + specialization.ordinal();
        result = result * 31 + status.ordinal();
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String completionDateString = formatter.format(completionDate);
        String creationDateString = formatter.format(creationDate);

        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append('{');
        sb.append("orderId=").append(orderId);
        sb.append(", userId=").append(userId);
        sb.append(", title= ").append(title);
        sb.append(", description= ").append(description);
        sb.append(", address= ").append(address);
        sb.append(", creationDate= ").append(creationDateString);
        sb.append(", completionDate= ").append(completionDateString);
        sb.append(", specialization= ").append(specialization);
        sb.append(", status=").append(status).append('}');
        return sb.toString();
    }


}
