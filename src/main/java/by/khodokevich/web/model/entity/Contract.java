package by.khodokevich.web.model.entity;

import java.util.Objects;

/**
 * Contract is the main entity we'll be using to outlining contract between customer and executor
 *
 * @author Oleg Khodokevich
 */
public class Contract extends Entity {
    private long idContract;
    private Order order;
    private User user;
    private ConcludedContractStatus concludedContractStatus;
    private CompletionContractStatus completionContractStatus;
    private Revoke revoke;


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

    public Revoke getRevoke() {
        return revoke;
    }

    public void setRevoke(Revoke revoke) {
        this.revoke = revoke;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract contract)) return false;
        return getIdContract() == contract.getIdContract() && getOrder().equals(contract.getOrder()) && getUser().equals(contract.getUser()) && getConcludedContractStatus() == contract.getConcludedContractStatus() && getCompletionContractStatus() == contract.getCompletionContractStatus();
    }

    @Override
    public int hashCode() {
        int result = (int) getIdContract() * 31 + order.hashCode();
        result = result * 31 + order.hashCode();
        result = result * 31 + completionContractStatus.ordinal();
        result = result * 31 + concludedContractStatus.ordinal();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{idContract=").append(idContract);
        sb.append(", order=").append(order);
        sb.append(", user=").append(user);
        sb.append(", concludedContractStatus=").append(concludedContractStatus);
        sb.append(", completionContractStatus=").append(completionContractStatus);
        sb.append('}');
        return sb.toString();
    }
}
