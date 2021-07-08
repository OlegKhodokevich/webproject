package by.khodokevich.web.command;

public enum CommandType {
    REGISTER,
    ACTIVATE,
    SIGN_IN,
    LOG_OUT,
    DEFAULT,
    SET_LOCALE,
    FIND_ORDERS_BY_SPECIALIZATIONS,

    GO_TO_MAIN,
    GO_TO_REGISTRATION,
    GO_TO_SIGN_IN,
    GO_TO_ERROR404,
    GO_TO_ORDER_PAGE,
}
