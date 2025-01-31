package com.altbank.utils;

import java.util.Random;
import java.util.UUID;

public class CardNumberUtil {
    private static final Random random = new Random();

    public static String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            cardNumber.append(random.nextInt(10));
        }

        int lastDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(lastDigit);

        return cardNumber.toString();
    }

    private static int calculateLuhnCheckDigit(String cardNumber) {
        int sum = 0;
        boolean shouldDouble = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = cardNumber.charAt(i) - '0';

            if (shouldDouble) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            shouldDouble = !shouldDouble;
        }

        return (10 - (sum % 10)) % 10;
    }

    public static int generateRandomThreeDigitNumber() {
        return 100 + random.nextInt(900);
    }

    public static String generateTrackingId(){
        return UUID.randomUUID().toString();
    }

}
