package ir.ac.kntu;

import java.util.*;

public class GetInfo {

    public static void userRole(Scanner scanner) {
        while (true) {
            System.out.println("Please select your role to log in.");
            System.out.println("1.Customer\n2.Seller\n3.Support\n4.Back");
            String inputString = scanner.nextLine();
            while (!inputString.matches("[1-4]")) {
                System.out.println("Please enter a number between 1 and 4.");
                inputString = scanner.nextLine();
            }
            switch (inputString) {
                case "1" -> checkSignUp(new Customer(), scanner);
                case "2" -> checkSignUp(new Seller(), scanner);
                case "3" -> Support.handleSupportLogIn(scanner);
                default -> {
                    return;
                }
            }
        }
    }

    public static void checkSignUp(User user, Scanner scanner) {
        System.out.println("Have you already signed up?");
        System.out.println("1.Yes\n2.No\n3.Back");
        String input = Search.getValidInput("Please enter a valid number", "[1-3]", scanner);
        switch (input) {
            case "1" -> user.handleLogIn(scanner);
            case "2" -> user.handleSignUp(user, scanner);
            default -> {
                return;
            }
        }
    }
}