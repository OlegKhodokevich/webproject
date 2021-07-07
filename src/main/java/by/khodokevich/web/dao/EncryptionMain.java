package by.khodokevich.web.dao;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionMain {
    public static void main(String[] args) {
        String passwordEncrypt = BCrypt.hashpw("password", BCrypt.gensalt(12));
        System.out.println(passwordEncrypt);
        System.out.println(BCrypt.checkpw("password",passwordEncrypt));
        System.out.println(BCrypt.hashpw("password", BCrypt.gensalt(12)));
    }
}
