package by.khodokevich.web.controller.command;

public record Router(String pagePath, RouterType routerType) {

    public enum RouterType {
        FORWARD, REDIRECT
    }
}