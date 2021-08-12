package by.khodokevich.web.controller.command;

import by.khodokevich.web.controller.command.impl.*;
import by.khodokevich.web.controller.command.impl.admin.ActivateUserCommand;
import by.khodokevich.web.controller.command.impl.admin.AllUserCommand;
import by.khodokevich.web.controller.command.impl.admin.FindUserInfoDetailCommand;
import by.khodokevich.web.controller.command.impl.admin.SetUserStatusCommand;
import by.khodokevich.web.controller.command.impl.common.*;
import by.khodokevich.web.controller.command.impl.user.*;
import by.khodokevich.web.model.entity.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;

import static by.khodokevich.web.model.entity.UserRole.*;

public class CommandProvider {
    private static final Logger logger = LogManager.getLogger(CommandProvider.class);
    private static CommandProvider instance;

    private CommandProvider() {
    }

    enum CommandType {
        REGISTER(new RegisterUserCommand(), List.of(GUEST)),
        ACTIVATE(new ActivateUserCommand(), List.of(ADMIN)),
        SIGN_IN(new SignInCommand(), List.of(GUEST)),
        LOG_OUT(new LogOutCommand(), List.of(EXECUTOR,CUSTOMER,ADMIN)),
        DEFAULT(new DefaultCommand(), List.of(UserRole.values())),
        SET_LOCALE(new SetLocaleCommand(), List.of(UserRole.values())),
        FIND_ORDERS_BY_SPECIALIZATIONS(new FindOrderBySpecializationCommand(), List.of(UserRole.values())),
        FIND_USER_ORDERS(new FindUserOrdersCommand(), List.of(CUSTOMER, ADMIN)),
        FIND_ORDER_INFO_DETAILS(new FindOrderInfoDetailsCommand(), List.of(UserRole.values())),
        ALL_ORDERS(new AllOrderCommand(), List.of(UserRole.values())),
        CREATE_ORDER(new CreateOrderCommand(), List.of(CUSTOMER)),
        ALL_EXECUTORS(new AllExecutorCommand(), List.of(UserRole.values())),
        FIND_EXECUTOR_INFO_DETAILS(new FindExecutorInfoDetailCommand(), List.of(UserRole.values())),
        FIND_USER_INFO_DETAILS(new FindUserInfoDetailCommand(), List.of(UserRole.values())),
        FIND_EXECUTORS_BY_SPECIALIZATIONS(new FindExecutorBySpecializationCommand(), List.of(UserRole.values())),
        ARCHIVE_ORDER(new ArchiveOrderCommand(), List.of(CUSTOMER,ADMIN)),
        ACTIVATE_ORDER(new ActivateOrderCommand(), List.of(CUSTOMER, ADMIN)),
        PREPARE_ACTIVATE_ORDER(new PrepareActivateEditOrderCommand(), List.of(CUSTOMER, ADMIN)),
        EDIT_ORDER(new EditOrderCommand(), List.of(CUSTOMER, ADMIN)),
        EDIT_USER(new EditUserCommand(), List.of(UserRole.values())),
        PREPARE_EDIT_USER(new PrepareEditUserCommand(), List.of(CUSTOMER, EXECUTOR, ADMIN)),
        ALL_USERS(new AllUserCommand(), List.of(ADMIN)),
        SET_USER_STATUS(new FindOrderInfoDetailsCommand(), List.of(UserRole.values())),
        FIND_CONTRACT_BY_CUSTOMER_ID(new FindContractByCustomerIdCommand(), List.of(CUSTOMER,ADMIN)),
        FIND_CONTRACT_BY_EXECUTOR_ID(new FindContractByExecutorIdCommand(), List.of(EXECUTOR,ADMIN)),
        FIND_OFFER_FOR_USER(new FindOfferForUserCommand(), List.of(CUSTOMER,ADMIN)),
        FIND_MY_OFFER(new FindMyOfferCommand(), List.of(EXECUTOR,ADMIN)),
        CONCLUDE_CONTRACT(new ConcludeContractCommand(), List.of(CUSTOMER,ADMIN)),
        DISMISS_CONTRACT(new DismissContractCommand(), List.of(CUSTOMER)),
        CLOSE_CONTRACT(new CloseContractCommand(), List.of(CUSTOMER)),
        CREATE_OFFER(new CreateOfferCommand(), List.of(EXECUTOR)),

        GO_TO_MAIN(new GoToMainCommand(), List.of(UserRole.values())),
        GO_TO_REGISTRATION(new GoToRegistrationCommand(), List.of(UserRole.values())),
        GO_TO_SIGN_IN(new GoToSignInCommand(), List.of(UserRole.values())),
        GO_TO_ERROR404(new GoToErrorUndefinedPageCommand(), List.of(UserRole.values())),
        GO_TO_CREATION_ORDER_PAGE(new GoToCreationOrderPage(), List.of(UserRole.values()));

        private Command command;
        private List<UserRole> roleList;

        CommandType(Command command, List<UserRole> roleList) {
            this.command = command;
            this.roleList = roleList;
        }

        public Command getCommand() {
            return command;
        }

        public List<UserRole> getRoleList() {
            return roleList;
        }
    }


//        commandMap.put(REGISTER, new RegisterUserCommand());
//        commandMap.put(ACTIVATE, new ActivateUserCommand());
//        commandMap.put(SIGN_IN, new SignInCommand());
//        commandMap.put(LOG_OUT, new LogOutCommand());
//        commandMap.put(DEFAULT, new DefaultCommand());
//        commandMap.put(SET_LOCALE, new SetLocaleCommand());
//
//        commandMap.put(GO_TO_MAIN, new GoToMainCommand());
//        commandMap.put(GO_TO_REGISTRATION, new GoToRegistrationCommand());
//        commandMap.put(GO_TO_SIGN_IN, new GoToSignInCommand());
//        commandMap.put(GO_TO_ERROR404, new GoToErrorUndefinedPageCommand());
//        commandMap.put(GO_TO_CREATION_ORDER_PAGE, new GoToCreationOrderPage());
//        commandMap.put(ALL_ORDERS, new AllOrderCommand());
//        commandMap.put(FIND_ORDERS_BY_SPECIALIZATIONS, new FindOrderBySpecializationCommand());
//        commandMap.put(FIND_ORDER_INFO_DETAILS, new FindOrderInfoDetailsCommand());
//        commandMap.put(FIND_USER_ORDERS, new FindUserOrdersCommand());
//        commandMap.put(CREATE_ORDER, new CreateOrderCommand());
//        commandMap.put(ALL_EXECUTORS, new AllExecutorCommand());
//        commandMap.put(FIND_EXECUTOR_INFO_DETAILS, new FindExecutorInfoDetailCommand());
//        commandMap.put(ARCHIVE_ORDER, new ArchiveOrderCommand());
//        commandMap.put(FIND_EXECUTORS_BY_SPECIALIZATIONS, new FindExecutorBySpecializationCommand());
//        commandMap.put(ACTIVATE_ORDER, new ActivateOrderCommand());
//        commandMap.put(PREPARE_ACTIVATE_ORDER, new PrepareActivateEditOrderCommand());
//        commandMap.put(EDIT_ORDER, new EditOrderCommand());
//        commandMap.put(FIND_USER_INFO_DETAILS, new FindUserInfoDetailCommand());
//        commandMap.put(PREPARE_EDIT_USER, new PrepareEditUserCommand());
//        commandMap.put(EDIT_USER, new EditUserCommand());
//        commandMap.put(ALL_USERS, new AllUserCommand());
//        commandMap.put(SET_USER_STATUS, new SetUserStatusCommand());
//        commandMap.put(FIND_CONTRACT_BY_CUSTOMER_ID, new FindContractByCustomerIdCommand());
//        commandMap.put(FIND_CONTRACT_BY_EXECUTOR_ID, new FindContractByExecutorIdCommand());
//        commandMap.put(FIND_MY_OFFER, new FindMyOfferCommand());
//        commandMap.put(FIND_OFFER_FOR_USER, new FindOfferForUserCommand());
//        commandMap.put(CONCLUDE_CONTRACT, new ConcludeContractCommand());
//        commandMap.put(DISMISS_CONTRACT, new DismissContractCommand());
//        commandMap.put(CLOSE_CONTRACT, new CloseContractCommand());
//        commandMap.put(CREATE_OFFER, new CreateOfferCommand());




    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandType commandType;
        if (commandName == null) {
            commandType = CommandType.DEFAULT;
        } else {
            try {
                commandType = CommandType.valueOf(commandName.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Command is unknown. Send to default page. ");
                commandType = CommandType.DEFAULT;
            }
        }
        return commandType.getCommand();
    }

    public List<UserRole> getRoleList(String commandName) {
        CommandType commandType;
        if (commandName == null) {
            commandType = CommandType.DEFAULT;
        } else {
            try {
                commandType = CommandType.valueOf(commandName.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Command is unknown. Send to default page. ");
                commandType = CommandType.DEFAULT;
            }
        }
        return commandType.getRoleList();
    }
}
