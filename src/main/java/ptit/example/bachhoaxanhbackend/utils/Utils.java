package ptit.example.bachhoaxanhbackend.utils;

import java.util.Random;

/**
 * Project: BachHoaXanhBackend
 * Author: Tran Van Tu
 * Date: 4/28/2022 11:27 AM
 * Desc:
 */
public class Utils {

    public static String generateOTP() {
        int len = 6;
        String numbers = "0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            result.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return result.toString();
    }
}
