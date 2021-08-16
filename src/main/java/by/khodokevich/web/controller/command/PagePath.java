package by.khodokevich.web.controller.command;

public final class PagePath {
    public static final String MAIN_PAGE = "/pages/main_page.jsp";
    public static final String REGISTER_PAGE = "/pages/registration.jsp";
    public static final String LOGIN_PAGE = "/pages/logging.jsp";
    public static final String ERROR_PAGE = "/pages/error.jsp";
    public static final String ERROR404_PAGE = "/pages/error404.jsp";
    public static final String ORDERS = "/pages/orders.jsp";
    public static final String ORDER_INFO = "/pages/order_info.jsp";
    public static final String MY_ORDERS = "/pages/user/my_orders.jsp";
    public static final String CREATION_ORDER_PAGE = "/pages/user/creation_order_page.jsp";
    public static final String EXECUTORS = "/pages/executors.jsp";
    public static final String EXECUTOR_INFO = "/pages/executor_info.jsp";
    public static final String USER_INFO = "/pages/user_info.jsp";
    public static final String ALL_USERS = "/pages/admin/all_users_page.jsp";
    public static final String MY_CONTRACTS = "/pages/user/my_contract.jsp";
    public static final String OFFER = "/pages/user/offer_for_user.jsp";
    public static final String REVOKE = "/pages/revokes.jsp";
    public static final String CREATE_REVOKE = "/pages/user/create_revoke_page.jsp";

    public static final String TO_ERROR_PAGE = "?command=go_to_error_page";
    public static final String TO_ERROR404_PAGE = "?command=go_to_error404";
    public static final String TO_ORDERS = "?command=all_orders";
    public static final String TO_USERS_ORDERS = "?command=find_user_orders";
    public static final String TO_MY_CONTRACT = "?command=find_contract_by_customer_id";
    public static final String TO_OFFER = "?command=find_offer_for_user";

    private PagePath() {
    }
}