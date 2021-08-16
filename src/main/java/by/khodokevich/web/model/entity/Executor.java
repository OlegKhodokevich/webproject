package by.khodokevich.web.model.entity;

/**
 * Executor is main entity which outlining user who take order and execute it.
 *
 * @author Oleg Khodokevich
 */
public class Executor extends User {

    private ExecutorOption executorOption;

    public Executor() {
    }

    public Executor(User user, ExecutorOption executorOption) {

        super(user.getIdUser(), user.getFirstName(), user.getLastName(), user.getEMail(), user.getPhone(), user.getRegion(), user.getCity(), user.getStatus(), user.getRole());
        this.executorOption = executorOption;
    }

    public Executor(String firstName, String lastName, String eMail, String phone, Region region, String city, UserStatus status, UserRole role, ExecutorOption executorOption) {
        super(firstName, lastName, eMail, phone, region, city, status, role);
        this.executorOption = executorOption;
    }

    public Executor(long idUser, String firstName, String lastName, String eMail, String phone, Region region, String city, UserStatus status, UserRole role, ExecutorOption executorOption) {
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
        return executor.getEMail() != null && super.getEMail().equals(executor.getEMail()) && executor.getFirstName() != null
                && super.getFirstName().equals(executor.getFirstName()) && executor.getLastName() != null
                && super.getLastName().equals(executor.getLastName()) && executor.getPhone() != null
                && super.getPhone().equals(executor.getPhone()) && executor.getRegion() != null
                && super.getRegion().equals(executor.getRegion()) && executor.getRole() != null
                && super.getRole().equals(executor.getRole()) && executor.getStatus() != null
                && super.getStatus().equals(executor.getStatus()) && executor.getCity() != null
                && super.getCity().equals(executor.getCity()) && executor.executorOption != null
                && executorOption.equals(executor.executorOption);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (executorOption != null ? executorOption.hashCode() : 0);
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
