package by.khodokevich.web.model.dao.impl;

/**
 * This class contains all column name of orders, specializations table which are used in DAO.
 *
 * @author Oleg Khodokevich
 */
public class OrderColumnName {

    protected static final String ID_ORDER = "IdOrder";
    protected static final String ID_CUSTOMER = "IdUserCustomer";
    protected static final String TITLE = "Title";
    protected static final String JOB_DESCRIPTION = "JobDescription";
    protected static final String ADDRESS = "Address";
    protected static final String CREATION_DATE = "CreationDate";
    protected static final String COMPLETION_DATE = "CompletionDate";
    protected static final String SPECIALIZATION = "Specialization";
    protected static final String ORDER_STATUS = "OrderStatus";

    private OrderColumnName() {
    }
}
