package ir.ac.kntu;

import java.util.*;

public class Support {

    private String name;
    private String userName;
    private String password;
    private SupportMenu menu;

    public Support(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.menu = new SupportMenu();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SupportMenu getMenu() {
        return menu;
    }

    public void setMenu(SupportMenu menu) {
        this.menu = menu;
    }

    public static void handleSupportLogIn(Scanner scanner) {
        String inputUserName = "";
        String inputPassword = "";
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Username\n2.Password\n3.Confirmation\n4.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    System.out.println("Please enter username.");
                    inputUserName = scanner.nextLine().trim();
                }
                case "2" -> {
                    System.out.println("Please enter password.");
                    inputPassword = scanner.nextLine().trim();
                }
                case "3" -> {
                    Support support = checkInformation(inputUserName, inputPassword);
                    if (support != null) {
                        support.menu.showSupportMenu(scanner);
                        return;
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public static Support checkInformation(String inputUserName, String inputPassword) {
        for (Support support : Database.getInstance().getSupports()) {
            if (support.getUserName().equals(inputUserName) && support.getPassword().equals(inputPassword)) {
                return support;
            }
        }
        System.out.println("Username or Password is wrong. Please try again.");
        return null;
    }
}
