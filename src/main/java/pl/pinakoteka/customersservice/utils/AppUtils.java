package pl.pinakoteka.customersservice.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {
    public static boolean validateData(String pattern, String data) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(data);
        return m.matches();
    }

    public static String generatePassword() {
        int passwordLength = 8;
        StringBuilder newPassword;
        String numbers = "0123456789";
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String signs = "!@#$*_";
        String allSigns = numbers + letters + letters.toUpperCase() + signs;

        do {
            newPassword = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < passwordLength; i++) {
                newPassword.append(allSigns.charAt(random.nextInt(allSigns.length())));
            }
        } while (!validateData(Consts.PASSWORD_PATTERN, newPassword.toString()));
        return newPassword.toString();
    }
}