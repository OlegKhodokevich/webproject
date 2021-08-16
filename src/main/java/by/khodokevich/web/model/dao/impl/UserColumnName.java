package by.khodokevich.web.model.dao.impl;

/**
 * This class contains all column name of users, regions table which are used in DAO.
 *
 * @author Oleg Khodokevich
 */
public class UserColumnName {
    protected static final String ID_USER = "IdUser";
    protected static final String FIRSTNAME = "FirstName";
    protected static final String LASTNAME = "LastName";
    protected static final String PASSWORD = "EncodedPassword";
    protected static final String E_MAIL = "EMail";
    protected static final String PHONE = "Phone";
    protected static final String REGION = "Region";
    protected static final String CITY = "City";
    protected static final String STATUS = "UserStatus";
    protected static final String ROLE_STATUS = "UserRole";

    private UserColumnName() {
    }
}
