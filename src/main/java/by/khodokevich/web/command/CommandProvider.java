package by.khodokevich.web.command;

import by.khodokevich.web.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

import static by.khodokevich.web.command.CommandType.*;

public class CommandProvider {
    private static final Logger logger = LogManager.getLogger();
    private static CommandProvider instance;
    private static EnumMap<CommandType, Command> commandMap = new EnumMap(CommandType.class);

    private CommandProvider() {
    }

    static {
        commandMap.put(REGISTER, new RegisterUserCommand());
        commandMap.put(ACTIVATE, new ActivateUserCommand());
        commandMap.put(SIGN_IN, new SignInCommand());
        commandMap.put(LOG_OUT, new LogOutCommand());
        commandMap.put(DEFAULT, new DefaultCommand());
        commandMap.put(SET_LOCALE, new SetLocaleCommand());

        commandMap.put(GO_TO_MAIN, new GoToMainCommand());
        commandMap.put(GO_TO_REGISTRATION, new GoToRegistrationCommand());
        commandMap.put(GO_TO_SIGN_IN, new GoToSignInCommand());
        commandMap.put(GO_TO_ERROR404, new GoToErrorUndefinedPageCommand());
        commandMap.put(GO_TO_ORDER_PAGE, new GoToOrderPageCommand());
        commandMap.put(FIND_ORDERS_BY_SPECIALIZATIONS, new FindOrdersBySpecializationCommand());
        commandMap.put(GO_TO_ORDER_INFO, new GoToOrderInfoPage());
        commandMap.put(GO_TO_MY_ORDERS, new GoToMyOrdersCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public Command getCommand(String commandName) {
        CommandType commandType;
        if (commandName == null) {
            commandType = DEFAULT;
        } else {
            try {
                commandType = CommandType.valueOf(commandName.toUpperCase());
            } catch (IllegalArgumentException e) {
                logger.error("Command is unknown. Send to default page. ");
                commandType = DEFAULT;
            }
        }
        return commandMap.get(commandType);
    }
}
