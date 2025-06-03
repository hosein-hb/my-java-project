package ir.ac.kntu;

import java.util.*;

public class Customer extends User {

    private String email;
    private ShoppingCart shoppingCart;
    private Addresses adress;
    private CustomerWallet wallet;
    private Orders orders;
    private SupportSection supportSection = new SupportSection();
    private CustomerMenu menu;

    public Customer() {
        shoppingCart = new ShoppingCart();
        adress = new Addresses();
        wallet = new CustomerWallet();
        orders = new Orders();
        supportSection = new SupportSection();
        menu = new CustomerMenu();
    }

    public SupportSection getSupportSection() {
        return supportSection;
    }

    public void setSupportSection(SupportSection supportSection) {
        this.supportSection = supportSection;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Addresses getAdress() {
        return adress;
    }

    public void setAdress(Addresses adress) {
        this.adress = adress;
    }

    public CustomerWallet getWallet() {
        return wallet;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public CustomerMenu getMenu() {
        return menu;
    }

    public void setMenu(CustomerMenu menu) {
        this.menu = menu;
    }

    public boolean checkEmailValidation(String email) {
        if (!email.matches("[A-Za-z0-9_.]+@gmail\\.com")) {
            System.out.println("Email format is invalid. Please try again.");
            return false;
        } else if (isEmailExist(email)) {
            System.out.println("This email already exists.");
            return false;
        }
        return true;
    }

    public boolean isEmailExist(String email) {
        for (User user : Database.getInstance().getUsers()) {
            if (!"Customers".equals(user.getRole())) {
                continue;
            }
            Customer customer = (Customer) user;
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public String readEmail(Scanner scanner) {
        System.out.println("Please enter email.(Enter 1 to return.)");
        String inputEmail = scanner.nextLine().trim();
        while (!checkEmailValidation(inputEmail)) {
            if (inputEmail.equals(getEmail())) {
                System.out.println("This email is already saved. Enter a new email or press 1 to exit.");
                inputEmail = scanner.nextLine().trim();
            }
            if ("B".equals(inputEmail)) {
                return getEmail();
            }
            if (checkEmailValidation(inputEmail)) {
                return inputEmail;
            }
            inputEmail = scanner.nextLine().trim();
        }
        return inputEmail;
    }

    @Override
    public void handleSignUp(User user, Scanner scanner) {
        Customer customer = (Customer) user;
        customer.setRole("Customer");
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.First name\n2.Last name\n3.Phone number\n4.Email\n5.Password\n6.Confirmation\n7.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 7.", "[1-7]", scanner);
            switch (input) {
                case "1" -> customer.setFirstName(readFirstName(scanner));
                case "2" -> customer.setLastName(readLastName(scanner));
                case "3" -> customer.setPhoneNumber(readPhone("Customer", scanner));
                case "4" -> customer.setEmail(readEmail(scanner));
                case "5" -> customer.setPassword(readPassword(scanner));
                case "6" -> {
                    if (customer.checkInputInformation()) {
                        Database.getInstance().addUser(customer);
                        return;
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public boolean checkInputInformation() {
        if (getFirstName() == null || getFirstName().isEmpty()) {
            System.out.println("First name field is empty.");
            return false;
        }
        if (getLastName() == null || getLastName().isEmpty()) {
            System.out.println("Last name field is empty.");
            return false;
        }
        if (getEmail() == null || getEmail().isEmpty()) {
            System.out.println("Email field is empty.");
            return false;
        }
        if (getPhoneNumber() == null || getPhoneNumber().isEmpty()) {
            System.out.println("Phone number field is empty.");
            return false;
        }
        if (getPassword() == null || getPassword().isEmpty()) {
            System.out.println("Password field is empty.");
            return false;
        }
        System.out.println("Sign up was successful.");
        return true;
    }

    @Override
    public void handleLogIn(Scanner scanner) {
        String inputUserName = "";
        String inputPassword = "";
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Username\n2.Password\n3.Confirmation\n4.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    System.out.println("Please enter email or phone number.");
                    inputUserName = scanner.nextLine().trim();
                }
                case "2" -> {
                    System.out.println("Please enter password.");
                    inputPassword = scanner.nextLine().trim();
                }
                case "3" -> {
                    Customer customer = findPassword(inputUserName, inputPassword);
                    if (customer != null) {
                        customer.getMenu().showCustomerMenu(customer, scanner);
                        return;
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public Customer findPassword(String inputUserName, String inputPassword) {
        for (User user : Database.getInstance().getUsers()) {
            if (!"Customer".equals(user.getRole())) {
                continue;
            }
            Customer customer = (Customer) user;
            if (customer.getPhoneNumber().equals(inputUserName) | customer.getEmail().equals(inputUserName)) {
                if (customer.getPassword().equals(inputPassword)) {
                    return customer;
                }
                break;
            }
        }
        System.out.println("Username or Password is wrong. Please try again.");
        return null;
    }

    public void editProfile(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("To edit the information for each section, enter the corresponding number.");
            System.out.println("1.First name\n2.Last name\n3.Phone number\n4.Email\n5.Password\n6.Back");
            String input = Search.getValidInput("Please enter a number between 1 and 6.", "[1-6]", scanner);
            switch (input) {
                case "1" -> customer.setFirstName(customer.readFirstName(scanner));
                case "2" -> customer.setLastName(customer.readLastName(scanner));
                case "3" -> customer.setPhoneNumber(customer.readPhone("Customer", scanner));
                case "4" -> customer.setEmail(customer.readEmail(scanner));
                case "5" -> customer.setPassword(customer.readPassword(scanner));
                default -> {
                    return;
                }
            }
        }
    }

    @Override
    public void operationOnSellectedGood(Scanner scanner, Good good) {
        System.out.println("1.Add to shopping cart\n2.back");
        String input = Search.getValidInput("Please enter a number between 1 and 2.", "[1-2]", scanner);
        if ("2".equals(input)) {
            return;
        }
        System.out.println("Please enter the order quantity: ");
        String quantity = Search.getValidInput("Please enter a valid input.", "[1-9][0-9]*", scanner);
        getShoppingCart().addShoppingList(good, Integer.parseInt(quantity));
    }

}