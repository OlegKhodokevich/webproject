package by.khodokevich.web.model.entity;

import java.util.*;

/**
 * Revoke is an entity customer will be using to give feedback.
 *
 * @author Oleg Khodokevich
 */
public class Revoke extends Entity {
    private long revokeId;
    private long contractId;
    private String description;
    private int mark;
    private Date creationDate;

    private static final List<Integer> markList = new ArrayList<>();

    static {
        markList.add(1);
        markList.add(2);
        markList.add(3);
        markList.add(4);
        markList.add(5);
    }

    public Revoke() {
    }

    public Revoke(long contractId, String description, int mark, Date creationDate) {
        this.contractId = contractId;
        this.description = description;
        this.mark = mark;
        this.creationDate = creationDate;
    }

    public Revoke(long revokeId, long contractId, String description, int mark, Date creationDate) {
        this.revokeId = revokeId;
        this.contractId = contractId;
        this.description = description;
        this.mark = mark;
        this.creationDate = creationDate;
    }

    public static List<Integer> getMarkList() {
        return Collections.unmodifiableList(markList);
    }

    public long getRevokeId() {
        return revokeId;
    }

    public void setRevokeId(long revokeId) {
        this.revokeId = revokeId;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Revoke revoke)) return false;
        return getRevokeId() == revoke.getRevokeId() && getContractId() == revoke.getContractId() && getMark() == revoke.getMark()
                && (revoke.getDescription() != null) ? getDescription().equals(revoke.getDescription()) : revoke.description == description
                && (revoke.getCreationDate() != null) ? getCreationDate().equals(revoke.getCreationDate()) : revoke.creationDate == creationDate;
    }

    @Override
    public int hashCode() {
        int result = (int) revokeId * 31 + (int) contractId;
        result = result * 31 + description.hashCode();
        result = result * 31 + mark;
        result = result * 31 + creationDate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{revokeId=").append(revokeId);
        sb.append(", description='").append(description).append('\'');
        sb.append(", contractId=").append(contractId);
        sb.append(", mark=").append(mark);
        sb.append('}');
        return sb.toString();
    }
}
