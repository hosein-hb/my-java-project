package ir.ac.kntu;

import java.util.*;

public class Seller extends User {

    private List<Good> salesList;
    private String storeTitle;
    private String province;
    private String sellerId;
    private String nationalNumber;
    private String supportAnswer;
    private boolean isChecked;
    private SellerWallet wallet;
    private List<SellerOrder> selledProducts;
    private SellerMenu sellerMenu;

    public Seller() {
        salesList = new ArrayList<>();
        supportAnswer = "Your information is being reviewd.";
        isChecked = false;
        selledProducts = new ArrayList<>();
        wallet = new SellerWallet();
        sellerMenu = new SellerMenu();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public SellerMenu getMenu() {
        return sellerMenu;
    }

    public List<SellerOrder> getSelledProducts() {
        return selledProducts;
    }

    public boolean addSelledProduct(SellerOrder selledProduct) {
        return selledProducts.add(selledProduct);
    }

    public SellerWallet getWallet() {
        return wallet;
    }

    public void setWallet(SellerWallet wallet) {
        this.wallet = wallet;
    }

    public List<Good> getSalesList() {
        return salesList;
    }

    public void addSalesList(Good good) {
        salesList.add(good);
    }

    public String getStoreTitle() {
        return storeTitle;
    }

    public void setStoreTitle(String storeTitle) {
        this.storeTitle = storeTitle;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(String nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getSupportAnswer() {
        return supportAnswer;
    }

    public void setSupportAnswer(String supportAnswer) {
        this.supportAnswer = supportAnswer;
    }

    public boolean checkNationalNumberValidation(String nationalNumber) {
        if (!nationalNumber.matches("\\d{10,10}")) {
            System.out.println("National number format is invalid. Please try again.");
            return false;
        }
        return true;
    }

    public String readNationalNumber(Scanner scanner) {
        System.out.println("Please enter national number.(Enter 1 to return)");
        String inputNumber = scanner.nextLine().trim();
        while (true) {
            if ("1".equals(inputNumber)) {
                return getNationalNumber();
            }
            if (checkNationalNumberValidation(inputNumber)) {
                return inputNumber;
            }
            inputNumber = scanner.nextLine().trim();
        }
    }

    public String readStoreTitle(Scanner scanner) {
        System.out.println("Please enter the store name. (Enter 1 to return)");
        String title = scanner.nextLine().trim();
        while (true) {
            if ("1".equals(title)) {
                return getStoreTitle();
            }
            if (title != null) {
                return title;
            }
            System.out.println("Please enter a valid title.");
            title = scanner.nextLine().trim();
        }
    }

    public String readProvince(Scanner scanner) {
        System.out.println("Please enter your province. (Enter 1 to return)");
        String inputProvince = scanner.nextLine().trim();
        while (true) {
            if ("1".equals(inputProvince)) {
                return getProvince();
            }
            if (inputProvince != null) {
                return inputProvince;
            }
            System.out.println("Please enter a valid title.");
            inputProvince = scanner.nextLine().trim();
        }
    }

    @Override
    public void handleSignUp(User user, Scanner scanner) {
        Seller seller = (Seller) user;
        seller.setRole("Seller");
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println(
                "1.First name\n2.Last name\n3.Phone number\n4.Title of store\n5.Password\n6.National Number\n7.Provinse\n8.Confirmation\n9.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 9.", "[1-9]", scanner);
            switch (input) {
                case "1" -> seller.setFirstName(readFirstName(scanner));
                case "2" -> seller.setLastName(readLastName(scanner));
                case "3" -> seller.setPhoneNumber(readPhone("Seller", scanner));
                case "4" -> seller.setStoreTitle(readStoreTitle(scanner));
                case "5" -> seller.setPassword(readPassword(scanner));
                case "6" -> seller.setNationalNumber(readNationalNumber(scanner));
                case "7" -> seller.setProvince(readProvince(scanner));
                case "8" -> {
                    if (signUpConfirmation(seller)) {
                        return;
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public boolean signUpConfirmation(Seller seller) {
        if(thereIsEmptyField()) {
            return false;
        }
        String generatedId = GenerateRandomCode.generateUniqueCode(Database.getInstance().getSellerCodes());
        seller.setSellerId(generatedId);
        Database.getInstance().addToWaitingList(seller);
        System.out.println("Your id is \"" + generatedId + "\" you have to use it for next log in.");
        return true;
    }

    public boolean thereIsEmptyField() {
        if (getFirstName() == null) {
            System.out.println("First name field is empty.");
            return true;
        }
        if (getLastName() == null) {
            System.out.println("Last name field is empty.");
            return true;
        }
        if (getStoreTitle() == null) {
            System.out.println("Email field is empty.");
            return true;
        }
        if (getPhoneNumber() == null) {
            System.out.println("Phone number field is empty.");
            return true;
        }
        if (getPassword() == null) {
            System.out.println("Password field is empty.");
            return true;
        }
        if (getProvince() == null) {
            System.out.println("Province field is empty.");
            return true;
        }
        if (getNationalNumber() == null) {
            System.out.println("National number field is empty.");
            return true;
        }
        System.out.println("Your information has been sent to support.");
        return false;
    }

    @Override
    public void handleLogIn(Scanner scanner) {
        String inputId = "", inputPassword = "";
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Store id\n2.Password\n3.Confirmation\n4.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    System.out.print("Please enter store id: ");
                    inputId = scanner.nextLine().trim();
                }
                case "2" -> {
                    System.out.print("Please enter password: ");
                    inputPassword = scanner.nextLine().trim();
                }
                case "3" -> {
                    if (checkSellerInfo(inputId, inputPassword, scanner)) {
                        return;
                    }
                    System.out.println("Store id or Password is wrong. Please try again.");
                }
                default -> {
                    return;
                }
            }
        }
    }

    public boolean checkSellerInfo(String inputId, String inputPassword, Scanner scanner) {
        Seller seller = findPasswordInUsers(inputId, inputPassword);
        if (seller != null) {
            getMenu().showSellerMenu(seller, scanner);
            return true;
        }
        seller = findPasswordInWatingList(inputId, inputPassword);
        if (seller == null) {
            return false;
        }
        if (seller.isChecked()) {
            editSignUpinfo(scanner, seller);
            return true;
        }
        System.out.println(seller.getSupportAnswer());
        return true;
    }

    public void editSignUpinfo(Scanner scanner, Seller seller) {
        System.out.println("Reason for rejection of registration request:\n" + seller.getSupportAnswer());
        System.out.println(
                "\nTo change the information in each section and resubmit the request, enter the corresponding number.");
        System.out.println("1.First name\n" +
                "2.Last name\n" +
                "3.Phone number\n" +
                "4.Title of store\n" +
                "5.Password\n" +
                "6.National Number\n" +
                "7.Provinse\n" +
                "8.Confirmation\n" +
                "9.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter a number between 1 and 9.", "[1-9]", scanner);
            switch (input) {
                case "1" -> seller.setFirstName(readFirstName(scanner));
                case "2" -> seller.setLastName(readLastName(scanner));
                case "3" -> seller.setPhoneNumber(readPhone("Seller", scanner));
                case "4" -> seller.setStoreTitle(readStoreTitle(scanner));
                case "5" -> seller.setPassword(readPassword(scanner));
                case "6" -> seller.setNationalNumber(readNationalNumber(scanner));
                case "7" -> seller.setProvince(readProvince(scanner));
                case "8" -> {
                    if (!seller.thereIsEmptyField()) {
                        seller.setChecked(false);
                        return;
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public Seller findPasswordInUsers(String inputId, String inputPassword) {
        for (User user : Database.getInstance().getUsers()) {
            if (!"Seller".equals(user.getRole())) {
                continue;
            }
            Seller seller = (Seller) user;
            if (seller.getSellerId().equals(inputId) && seller.getPassword().equals(inputPassword)) {
                return seller;
            }
        }
        return null;
    }

    public Seller findPasswordInWatingList(String inputId, String inputPassword) {
        for (Seller seller : Database.getInstance().getWaitingList()) {
            if (seller.getSellerId().equals(inputId) && seller.getPassword().equals(inputPassword)) {
                return seller;
            }
        }
        return null;
    }

    public void printFullInformation() {
        System.out.println("Seller Information:");
        System.out.println("   - Seller Firstname: " + getFirstName());
        System.out.println("   - Seller Lastname: " + getLastName());
        System.out.println("   - Store: " + getStoreTitle());
        System.out.println("   - Province: " + getProvince());
        System.out.println("   - National Number: " + getNationalNumber());
        System.out.println("   - Phone: " + getPhoneNumber());
    }

    @Override
    public void operationOnSellectedGood(Scanner scanner, Good good) {
        System.out.println("Set a new inventory for your product.(enter B to return)");
        String input = Search.getValidInput("Please enter a valid number.", "(0|[1-9][0-9]*)|B", scanner);
        if ("B".equals(input)) {
            return;
        }
        good.setNumber(Integer.parseInt(input));
    }

}