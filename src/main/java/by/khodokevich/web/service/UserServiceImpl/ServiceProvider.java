package by.khodokevich.web.service.UserServiceImpl;

import by.khodokevich.web.service.ExecutorService;
import by.khodokevich.web.service.OrderService;
import by.khodokevich.web.service.UserService;
import by.khodokevich.web.service.UserServiceImpl.OrderServiceImpl;
import by.khodokevich.web.service.UserServiceImpl.UserServiceImpl;

public class ServiceProvider {
    public static final UserService USER_SERVICE = new UserServiceImpl();
    public static final OrderService ORDER_SERVICE = new OrderServiceImpl();
    public static final ExecutorService EXECUTOR_SERVICE = new ExecutorServiceImpl();

    private ServiceProvider() {
    }
}
