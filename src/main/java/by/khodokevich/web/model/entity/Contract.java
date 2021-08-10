package by.khodokevich.web.model.entity;

import java.util.Objects;

public class Contract extends Entity {
    private long idContract;
    private Order order;
    private User user;
    private ConcludedContractStatus concludedContractStatus;
    private CompletionContractStatus completionContractStatus;

    public enum CompletionContractStatus {
        COMPLETED("contract.yes"),
        NOT_COMPLETED("contract.no");

        private final String key;

        CompletionContractStatus(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public enum ConcludedContractStatus {
        UNDER_CONSIDERATION("contract.under_consideration"),
        CONCLUDED("contract.yes"),
        NOT_CONCLUDED("contract.dismiss");

        private final String key;

        ConcludedContractStatus(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public Contract() {
    }
    public Contract(long idContract, ConcludedContractStatus concludedContractStatus, CompletionContractStatus completionContractStatus) {
        this.idContract = idContract;
        this.concludedContractStatus = concludedContractStatus;
        this.completionContractStatus = completionContractStatus;
    }
    public Contract(long idContract, Order order, User user, ConcludedContractStatus concludedContractStatus, CompletionContractStatus completionContractStatus) {
        this.idContract = idContract;
        this.order = order;
        this.user = user;
        this.concludedContractStatus = concludedContractStatus;
        this.completionContractStatus = completionContractStatus;
    }

    public long getIdContract() {
        return idContract;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConcludedContractStatus getConcludedContractStatus() {
        return concludedContractStatus;
    }

    public void setConcludedContractStatus(ConcludedContractStatus concludedContractStatus) {
        this.concludedContractStatus = concludedContractStatus;
    }

    public CompletionContractStatus getCompletionContractStatus() {
        return completionContractStatus;
    }

    public void setCompletionContractStatus(CompletionContractStatus completionContractStatus) {
        this.completionContractStatus = completionContractStatus;
    }

    @Override
    public boolean equals(Object o) {       //TODO
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;
        Contract contract = (Contract) o;
        return getIdContract() == contract.getIdContract() && Objects.equals(getOrder(), contract.getOrder()) && Objects.equals(getUser(), contract.getUser()) && getConcludedContractStatus() == contract.getConcludedContractStatus() && getCompletionContractStatus() == contract.getCompletionContractStatus();
    }

    @Override
    public int hashCode() {         //TODO
        return Objects.hash(getIdContract(), getOrder(), getUser(), getConcludedContractStatus(), getCompletionContractStatus());
    }

    @Override
    public String toString() {      //TODO
        final StringBuilder sb = new StringBuilder("Contract{");
        sb.append("idContract=").append(idContract);
        sb.append(", order=").append(order);
        sb.append(", user=").append(user);
        sb.append(", concludedContractStatus=").append(concludedContractStatus);
        sb.append(", completionContractStatus=").append(completionContractStatus);
        sb.append('}');
        return sb.toString();
    }
}
