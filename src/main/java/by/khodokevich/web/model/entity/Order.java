package by.khodokevich.web.model.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Order is entity which contain information about job.
 *
 * @author Oleg Khodokevich
 */
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

    public Order(long orderId) {
        this.orderId = orderId;
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

    public void  closeOrder(){
        status.close(this);
    }

    public void openOrder(){
        status.open(this);
    }

    public void workingOrder(){
        status.working(this);
    }

    public void considering(){
        status.considering(this);
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && userId == order.userId && order.title != null ? title.equals(order.title) : order.title == title
                && order.description != null ? description.equals(order.description) : order.description == description
                && order.address != null ? address.equals(order.address) : order.address == address
                && order.creationDate != null ? creationDate.equals(order.creationDate) : order.creationDate == creationDate
                && order.completionDate != null ? completionDate.equals(order.completionDate) : order.completionDate == completionDate
                && order.specialization != null ? specialization == order.specialization : order.specialization == specialization
                && status == order.status;
    }

    @Override
    public int hashCode() {
        int result = (int) orderId + 31 * title.hashCode();
        result = result * 31 + (int) userId;
        result = result * 31 + (description != null ? description.hashCode() : 0);
        result = result * 31 + (address != null ? address.hashCode() : 0);
        result = result * 31 + (creationDate != null ? creationDate.hashCode() : 0);
        result = result * 31 + (completionDate != null ? completionDate.hashCode() : 0);
        result = result * 31 + (specialization != null ? specialization.hashCode() : 0);
        result = result * 31 + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String completionDateString;
        if (completionDate != null) {
            completionDateString = formatter.format(completionDate);
        } else {
            completionDateString = "";
        }
        String creationDateString;
        if (creationDate != null) {
            creationDateString = formatter.format(creationDate);
        } else {
            creationDateString = "";
        }

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
