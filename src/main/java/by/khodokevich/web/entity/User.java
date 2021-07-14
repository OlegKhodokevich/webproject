package by.khodokevich.web.entity;

public class User extends Entity{
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

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
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
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && firstName.equals(firstName) && lastName.equals(lastName)&& eMail.equals(user.eMail) && phone.equals(user.phone) && region == user.region && city.equals(user.city) && status == user.status && role == user.role;
    }

    @Override
    public int hashCode() {
        int result = (int)userId + 31 * firstName.hashCode();
        result = result * 31 + lastName.hashCode();
        result = result * 31 + eMail.hashCode();
        result = result * 31 + phone.hashCode();
        result = result * 31 + region.ordinal();
        result = result * 31 + city.hashCode();
        result = result * 31 + status.ordinal();
        result = result * 31 + role.ordinal();
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
