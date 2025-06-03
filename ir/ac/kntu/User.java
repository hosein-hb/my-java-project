package ir.ac.kntu;

import java.util.*;

public abstract class User {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private String role;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean checkNameFormat(String firstName) {
        if (!firstName.matches("[A-Za-z\s]+")) {
            System.out.println("The name must contain only letters. Please try again.");
            return false;
        }
        return true;
    }

    public boolean checkPhoneNumberValidation(String role, String phoneNumber) {
        if (!phoneNumber.matches("09\\d{9,9}")) {
            System.out.println("Phone number format is invalid. Please try again.");
            return false;
        }
        if (isPhoneNumberExist(role, phoneNumber)) {
            System.out.println("This phone number already exists.");
            return false;
        }
        return true;
    }

    public boolean isPhoneNumberExist(String role, String phoneNumber) {
        for (User user : Database.getInstance().getUsers()) {
            if (user.getRole().equals(role) && user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPasswordSecurity(String password) {
        return password.length() >= 8
                && password.matches(".*[a-z].*")
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^A-Za-z0-9].*");
    }

    public String readFirstName(Scanner scanner) {
        System.out.println("Please enter first name.(Enter 1 to return)");
        String inputFirstName = scanner.nextLine().trim();
        while (true) {
            if ("1".equals(inputFirstName)) {
                return getFirstName();
            }
            if (checkNameFormat(inputFirstName)) {
                return inputFirstName;
            }
            inputFirstName = scanner.nextLine().trim();
        }
    }

    public String readLastName(Scanner scanner) {
        System.out.println("Please enter last name.(Enter 1 to return)");
        String inputLastName = scanner.nextLine();
        while (true) {
            if ("1".equals(inputLastName)) {
                return getLastName();
            }
            if (checkNameFormat(inputLastName)) {
                return inputLastName;
            }
            inputLastName = scanner.nextLine().trim();
        }
    }

    public String readPhone(String role, Scanner scanner) {
        System.out.println("Please enter phone number.(Enter B to return)");
        String inputPhoneNumber = scanner.nextLine().trim();
        while (true) {
            if (inputPhoneNumber.equals(getPhoneNumber())) {
                System.out.println("This number is already saved. Enter a new number or press B to exit.");
                inputPhoneNumber = scanner.nextLine().trim();
                continue;
            }
            if ("B".equals(inputPhoneNumber)) {
                return getPhoneNumber();
            }
            if (checkPhoneNumberValidation(role, inputPhoneNumber)) {
                return inputPhoneNumber;
            }
            inputPhoneNumber = scanner.nextLine().trim();
        }
    }

    public String readPassword(Scanner scanner) {
        System.out.println("Please enter password.(Enter B to return)");
        String inputPassword = scanner.nextLine().trim();
        while (true) {
            if ("B".equals(inputPassword)) {
                return getPassword();
            }
            if (checkPasswordSecurity(inputPassword)) {
                return inputPassword;
            }
            System.out.println("The password must be at least 8 characters long and " +
                    "include one uppercase and lowercase letter, one number, " +
                    "and one special character. Please try again.");
            inputPassword = scanner.nextLine().trim();
        }
    }

    public abstract void handleSignUp(User user, Scanner scanner);

    public abstract void handleLogIn(Scanner scanner);

    public abstract void operationOnSellectedGood(Scanner scanner, Good good);

}