package by.khodokevich.web.model.builder;

import by.khodokevich.web.model.entity.*;

public class ExecutorBuilder {
    private long userId;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private RegionBelarus region;
    private String city;
    private UserStatus status;
    private UserRole role;

    private ExecutorOption executorOption;

    public ExecutorBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public ExecutorBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ExecutorBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ExecutorBuilder eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public ExecutorBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public ExecutorBuilder region(RegionBelarus region) {
        this.region = region;
        return this;
    }

    public ExecutorBuilder city(String city) {
        this.city = city;
        return this;
    }

    public ExecutorBuilder status(UserStatus status) {
        this.status = status;
        return this;
    }

    public ExecutorBuilder role(UserRole role) {
        this.role = role;
        return this;
    }

    public ExecutorBuilder executorOption(ExecutorOption executorOption) {
        this.executorOption = executorOption;
        return this;
    }

    public Executor buildExecutor() {
        return new Executor(userId, firstName, lastName, eMail, phone, region, city, status, role, executorOption);
    }
}
