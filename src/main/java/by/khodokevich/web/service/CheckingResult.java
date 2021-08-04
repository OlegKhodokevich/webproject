package by.khodokevich.web.service;

public enum CheckingResult {
    DUPLICATE_PHONE,
    DUPLICATE_EMAIL,
    DUPLICATE_EMAIL_AND_PHONE,
    SUCCESS,
    SUCCESS_WITHOUT_SEND_EMAIL,
    ERROR,
    USER_UNKNOWN,
    USER_STATUS_NOT_DECLARED,
    USER_STATUS_NOT_CONFIRM,
    USER_STATUS_IS_ARCHIVED,
    NOT_VALID

}