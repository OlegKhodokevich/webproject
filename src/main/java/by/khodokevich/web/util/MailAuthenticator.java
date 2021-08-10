package by.khodokevich.web.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class MailAuthenticator {
    private static final Logger logger = LogManager.getLogger(MailAuthenticator.class);
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE_NAME = "mail.properties";
    private static final String SPECIFY_E_MAIL_NAME_PROPERTIES = "mail.from";
    private static final String SPECIFY_PASSWORD_NAME_PROPERTIES = "mail.password";
    private static final String AGENT_NAME = "mail.user_name";

    static {
        try (InputStream inputStream = MailAuthenticator
                .class.getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Can't read properties for sending e-mail.", e);
        }
    }

    public static void sendEmail(String eMail, String theme, String text) throws MessagingException {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty(AGENT_NAME), properties.getProperty(SPECIFY_PASSWORD_NAME_PROPERTIES));
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(properties.getProperty(SPECIFY_E_MAIL_NAME_PROPERTIES)));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail));
        message.setSubject(theme);
        message.setText(text);
        Transport.send(message);

    }
}
