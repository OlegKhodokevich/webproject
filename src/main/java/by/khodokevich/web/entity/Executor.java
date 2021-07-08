package by.khodokevich.web.entity;

import java.util.Objects;

public class Executor extends User {

    private ExecutorOption executorOption;

    public Executor() {
    }

    public Executor(String login, String encryptedPassword, String eMail, String phone, RegionBelarus region, String city, UserStatus status, ExecutorOption executorOption) {
        super(login, encryptedPassword, eMail, phone, region, city, status, UserRole.EXECUTOR);
        this.executorOption = executorOption;
    }

    public Executor(long idUser, String login, String encryptedPassword, String eMail, String phone, RegionBelarus region, String city, UserStatus status, ExecutorOption executorOption) {
        super(idUser, login, encryptedPassword, eMail, phone, region, city, status, UserRole.EXECUTOR);
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
