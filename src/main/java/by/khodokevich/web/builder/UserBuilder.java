package by.khodokevich.web.builder;

import by.khodokevich.web.entity.RegionBelarus;
import by.khodokevich.web.entity.User;
import by.khodokevich.web.entity.UserRole;
import by.khodokevich.web.entity.UserStatus;

import java.util.Locale;

public class UserBuilder {
    private long userId;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private RegionBelarus region;
    private String city;
    private UserStatus status;
    private UserRole role;

    public UserBuilder() {
    }

    public UserBuilder userId(long userId) {
        this.userId = userId;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder eMail(String eMail) {
        this.eMail = eMail;
        return this;
    }

    public UserBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder region(RegionBelarus region) {
        this.region = region;
        return this;
    }

    public UserBuilder city(String city) {
        this.city = city;
        return this;
    }

    public UserBuilder status(UserStatus status) {
        this.status = status;
        return this;
    }

    public UserBuilder role(UserRole role) {
        this.role = role;
        return this;
    }

    public User buildUserWithoutId() {
        return new User(firstName, lastName, eMail, phone, region, city, status, role);
    }

    public User buildUser() {
        return new User(userId, firstName, lastName, eMail, phone, region, city, status, role);
    }
}
