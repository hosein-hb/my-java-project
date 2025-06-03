package ir.ac.kntu;

import java.util.*;

public class GenerateRandomCode {

    public static String generateUniqueCode(Set<String> codes) {
        String code;
        do {
            code = generateCode();
        } while (codes.add(code));
        return code;
    }

    private static String generateCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }
        return code.toString();
    }

}
