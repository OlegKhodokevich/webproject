package by.khodokevich.web.model.entity;

/**
 * User is main entity which outlining all application users (customer, admin) and can be extended to executor.
 * Customer, Admin, Executor are user's role.
 * Customer can create order, manage contracts.
 * Executor can create offer.
 *
 * @author Oleg Khodokevich
 */
public class User extends Entity {
    private long userId;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private RegionBelarus region;
    private String city;
    private UserStatus status;
    private UserRole role;

    public User() {
    }

    public User(long userId) {
        this.userId = userId;
    }

    public User(String firstName, String lastName, String eMail, String phone, RegionBelarus region, String city, UserStatus status, UserRole role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phone = phone;
        this.region = region;
        this.city = city;
        this.status = status;
        this.role = role;
    }

    public User(long idUser, String firstName, String lastName, String eMail, String phone, RegionBelarus region, String city, UserStatus status, UserRole role) {
        this.userId = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phone = phone;
        this.region = region;
        this.city = city;
        this.status = status;
        this.role = role;
    }

    public long getIdUser() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RegionBelarus getRegion() {
        return region;
    }

    public void setRegion(RegionBelarus region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return userId == user.userId && user.firstName != null && firstName.equals(user.firstName) && user.lastName != null
                && lastName.equals(user.lastName) && user.eMail != null && eMail.equals(user.eMail) && user.phone != null
                && phone.equals(user.phone) && user.region != null && region == user.region && user.city != null
                && city.equals(user.city) && user.status != null && status == user.status && user.role != null && role == user.role;
    }

    @Override
    public int hashCode() {
        int result = (int) userId + 31 * (firstName != null ? firstName.hashCode() : 0);
        result = result * 31 + (lastName != null ? lastName.hashCode() : 0);
        result = result * 31 + (eMail != null ? eMail.hashCode() : 0);
        result = result * 31 + (phone != null ? phone.hashCode() : 0);
        result = result * 31 + (region != null ? region.ordinal() : 0);
        result = result * 31 + (city != null ? city.hashCode() : 0);
        result = result * 31 + (status != null ? status.ordinal() : 0);
        result = result * 31 + (role != null ? role.ordinal() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" {idUser=").append(userId);
        sb.append(", firstName= ").append(firstName);
        sb.append(", lastName= ").append(lastName);
        sb.append(", eMail= ").append(eMail);
        sb.append(", phone= ").append(phone);
        sb.append(", region= ").append(region);
        sb.append(", city= ").append(city);
        sb.append(", status= ").append(status);
        sb.append(", role= ").append(role).append('}');
        return sb.toString();
    }
}
