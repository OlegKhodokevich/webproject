package by.khodokevich.web.model.service;

import by.khodokevich.web.model.service.impl.*;

/**
 * Class provide User
 */
public class ServiceProvider {
    public static final UserService USER_SERVICE = UserServiceImpl.getInstance();
    public static final OrderService ORDER_SERVICE = OrderServiceImpl.getInstance();
    public static final ExecutorService EXECUTOR_SERVICE = ExecutorServiceImpl.getInstance();
    public static final ContractService CONTRACT_SERVICE = ContractServiceImpl.getInstance();
    public static final RevokeService REVOKE_SERVICE = RevokeServiceImpl.getInstance();

    private ServiceProvider() {
    }
}
