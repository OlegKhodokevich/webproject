package by.khodokevich.web.command;

public class PagePath {
    public static final String MAIN_PAGE = "/pages/main_page.jsp";
    public static final String REGISTER_PAGE = "/pages/registration.jsp";
    public static final String LOGIN_PAGE = "/pages/logging.jsp";
    public static final String ERROR_PAGE = "/pages/error.jsp";
    public static final String ERROR404_PAGE = "/pages/error404.jsp";
    public static final String ORDERS = "/pages/orders.jsp";
    public static final String ORDER_INFO = "/pages/order_info.jsp";
    public static final String MY_ORDERS = "/pages/my_orders.jsp";
    public static final String CREATION_ORDER_PAGE = "/pages/creation_order_page.jsp";

    public static final String TO_MAIN_PAGE = "?command=go_to_main";
    public static final String TO_REGISTER_PAGE = "?command=go_to_registration";
    public static final String TO_LOGIN_PAGE = "?command=go_to_sign_in";
    public static final String TO_ERROR_PAGE = "?command=go_to_error_page";
    public static final String TO_ERROR404_PAGE = "?command=go_to_error404";
    public static final String TO_ORDERS = "?command=go_to_order_page";
    public static final String TO_ORDER_INFO = "?command=go_to_order_page";


    public static final String COMMAND_MY_ORDERS = "?command=my_orders";
//    public static final String MY_ORDERS = "/pages/my_orders.jsp";


    private PagePath() {
    }
}