package by.khodokevich.web.entity;

import java.util.Objects;

public class Contract extends Entity {
    private long idContract;
    private long idOrder;
    private long idExecutor;
    private ConcludedContractStatus concludedContractStatus;
    private CompletionContractStatus completionContractStatus;

    public Contract() {
    }

    public Contract(long idOrder, long idExecutor, ConcludedContractStatus concludedContractStatus, CompletionContractStatus completionContractStatus) {
        this.idOrder = idOrder;
        this.idExecutor = idExecutor;
        this.concludedContractStatus = concludedContractStatus;
        this.completionContractStatus = completionContractStatus;
    }

    public Contract(long idContract, long idOrder, long idExecutor, ConcludedContractStatus concludedContractStatus, CompletionContractStatus completionContractStatus) {
        this.idContract = idContract;
        this.idOrder = idOrder;
        this.idExecutor = idExecutor;
        this.concludedContractStatus = concludedContractStatus;
        this.completionContractStatus = completionContractStatus;
    }

    public long getIdContract() {
        return idContract;
    }


    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdExecutor() {
        return idExecutor;
    }

    public void setIdExecutor(long idExecutor) {
        this.idExecutor = idExecutor;
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
    public boolean equals(Object o) {               //TODO change implementation
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return idContract == contract.idContract && idOrder == contract.idOrder && idExecutor == contract.idExecutor && concludedContractStatus == contract.concludedContractStatus && completionContractStatus == contract.completionContractStatus;
    }

    @Override
    public int hashCode() {              //TODO change implementation
        return Objects.hash(idContract, idOrder, idExecutor, concludedContractStatus, completionContractStatus);
    }

    @Override
    public String toString() {                    //TODO change implementation
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append('{');
        sb.append("idContract=").append(idContract);
        sb.append(", idExecutor=").append(idExecutor);
        sb.append(", concludedContractStatus=").append(concludedContractStatus);
        sb.append(", completionContractStatus=").append(completionContractStatus).append('}');

        return sb.toString();
    }
}
