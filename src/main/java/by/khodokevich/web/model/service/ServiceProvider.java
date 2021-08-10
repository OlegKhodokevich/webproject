package by.khodokevich.web.model.service;

import by.khodokevich.web.model.service.Impl.ContractServiceImpl;
import by.khodokevich.web.model.service.Impl.ExecutorServiceImpl;
import by.khodokevich.web.model.service.Impl.OrderServiceImpl;
import by.khodokevich.web.model.service.Impl.UserServiceImpl;

public class ServiceProvider {
    public static final UserService USER_SERVICE = UserServiceImpl.getInstance();
    public static final OrderService ORDER_SERVICE = OrderServiceImpl.getInstance();
    public static final ExecutorService EXECUTOR_SERVICE = ExecutorServiceImpl.getInstance();
    public static final ContractService CONTRACT_SERVICE = ContractServiceImpl.getInstance();

    private ServiceProvider() {
    }
}
