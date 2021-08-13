package by.khodokevich.web.model.entity;

/**
 * Contract is the main entity we'll be using to outlining contract between customer and executor
 *
 * @author Oleg Khodokevich
 *
 */
public class Executor extends User {

    private ExecutorOption executorOption;

    public Executor() {
    }

    public Executor(User user, ExecutorOption executorOption) {

        super(user.getIdUser(), user.getFirstName(), user.getLastName(), user.getEMail(), user.getPhone(), user.getRegion(), user.getCity(), user.getStatus(), user.getRole());
        this.executorOption = executorOption;
    }

    public Executor(String firstName, String lastName, String eMail, String phone, RegionBelarus region, String city, UserStatus status, UserRole role, ExecutorOption executorOption) {
        super(firstName, lastName, eMail, phone, region, city, status, role);
        this.executorOption = executorOption;
    }

    public Executor(long idUser, String firstName, String lastName, String eMail, String phone, RegionBelarus region, String city, UserStatus status, UserRole role, ExecutorOption executorOption) {
        super(idUser, firstName, lastName, eMail, phone, region, city, status, role);
        this.executorOption = executorOption;
    }

    public ExecutorOption getExecutorOption() {
        return executorOption;
    }

    public void setExecutorOption(ExecutorOption executorOption) {
        this.executorOption = executorOption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Executor executor)) return false;
        if (!super.equals(o)) return false;
        return super.getEMail().equals(executor.getEMail()) && super.getFirstName().equals(executor.getFirstName())
                && super.getLastName().equals(executor.getLastName()) && super.getPhone().equals(executor.getPhone())
                && super.getRegion().equals(executor.getRegion()) && super.getRole().equals(executor.getRole())
                && super.getStatus().equals(executor.getStatus()) && super.getCity().equals(executor.getCity())
                && executorOption.equals(executor.executorOption);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + executorOption.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        sb.append(super.toString());
        sb.append("executorOption=").append(executorOption);
        sb.append('}');
        return sb.toString();
    }
}
