package by.khodokevich.web.service;

public enum CheckingResult {
    DUPLICATE_PHONE,
    DUPLICATE_EMAIL,
    DUPLICATE_EMAIL_AND_PHONE,
    SUCCESS,
    ERROR,
    USER_UNKNOWN,
    USER_STATUS_NOT_DECLARED,
    USER_STATUS_NOT_CONFIRM,
    NOT_VALID

}
