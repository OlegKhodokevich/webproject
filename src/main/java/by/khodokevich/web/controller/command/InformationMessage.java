package by.khodokevich.web.controller.command;

public final class InformationMessage {
    public static final String USER_ACTIVATE = "activate.user_activate";
    public static final String ERROR_USER_UNKNOWN = "activate.error_user_unknown";
    public static final String ERROR_USER_STATUS_NOT_DECLARED = "activate.error_user_status_not_declared";
    public static final String USER_NOT_FOUND = "user.user_not_found";
    public static final String EXECUTOR_NOT_FOUND = "executor.empty_list";
    public static final String ORDERS_NOT_FOUND = "order.empty_list";
    public static final String KEY_SENT_MASSAGE = "registration.message_send_link";
    public static final String KEY_MESSAGE_DATA_NOT_CORRECT = "registration.message_data_not_correct";
    public static final String KEY_MESSAGE_DUPLICATE_EMAIL = "registration.message_duplicate_email";
    public static final String KEY_MESSAGE_DUPLICATE_PHONE = "registration.message_duplicate_phone";
    public static final String KEY_MESSAGE_DUPLICATE_PHONE_AND_PHONE = "registration.message_duplicate_email_and_phone";
    public static final String MASSAGE_LETTER_NOT_SENT = "mail_sender.letter_not_send";
    public static final String KEY_STATUS_ARCHIVED = "error.status_archived";
    public static final String KEY_DATA_NOT_VALID = "registration.message_data_not_correct";
    public static final String KEY_USER_UNKNOWN = "error.user_unknown";
    public static final String KEY_USER_NOT_CONFIRMED = "error.user_not_confirmed";
    public static final String KEY_ORDER_ACTIVATED = "order.message_activate";
    public static final String KEY_DATA_INCORRECT = "order.message_data_incorrect";
    public static final String KEY_UNKNOWN_USER = "order.message_user_unknown";
    public static final String KEY_MESSAGE_SUCCESS = "contract.offer_created";
    public static final String KEY_MESSAGE_RESENDING = "contract.double_offer";
    public static final String KEY_ORDER_CREATED = "order.message_created";
    public static final String KEY_ORDER_EDIT = "order.message_edit";
    public static final String MASSAGE_USER_UNKNOWN = "error.user_unknown_old_password_wrong";
    public static final String MESSAGE_UNSUPPORTED_OPERATION = "project.unsupported_operation";
    public static final String EDIT_REST_DATA = "executor.edit_info";
    public static final String MASSAGE_KEY = "error.status_archived";
    public static final String WELCOME_USER_MESSAGE = "logging.welcome_user";
    public static final String WELCOME_ADMIN_MESSAGE = "logging.welcome_admin";

    private InformationMessage() {
    }
}
