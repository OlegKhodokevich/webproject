package by.khodokevich.web.controller.command;

/**
 * This class for unification information about router for controller.
 */
public record Router(String pagePath, RouterType routerType) {

    public enum RouterType {
        FORWARD, REDIRECT
    }
}