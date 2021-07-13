package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.OrderServiceImpl;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;

public class ServiceProvider {
    public static final UserService USER_SERVICE = new UserServiceImpl();
    public static final OrderService ORDER_SERVICE = new OrderServiceImpl();

    private ServiceProvider() {
    }
}
