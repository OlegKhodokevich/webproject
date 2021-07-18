package by.khodokevich.web.entity;

import java.util.Objects;

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
    public boolean equals(Object o) {                   //TODO change implementation
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Executor executor = (Executor) o;
        return Objects.equals(executorOption, executor.executorOption);
    }

    @Override
    public int hashCode() {                    //TODO change implementation
        return Objects.hash(super.hashCode(), executorOption);
    }

    @Override
    public String toString() {                                  //TODO change implementation
        return "Executor{" + super.toString() +
                "executorOption=" + executorOption +
                '}';
    }
}
