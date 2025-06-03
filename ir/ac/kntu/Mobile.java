package ir.ac.kntu;

import java.util.*;

public class Mobile extends Digital {

    private String frontResolution;
    private String mainResolution;
    private String networkType;

    public String getFrontResolution() {
        return frontResolution;
    }

    public void setFrontResolution(String frontResolution) {
        this.frontResolution = frontResolution;
    }

    public String getMainResolution() {
        return mainResolution;
    }

    public void setMainResolution(String mainResolution) {
        this.mainResolution = mainResolution;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    @Override
    public void search(Filter filter, Scanner scanner, Customer customer) {
        List<Mobile> mobiles = getMobileList();
        if (filter.isFilter()) {
            Search.priceFilter(filter.getMinPrice(), filter.getMaxPrice(), mobiles);
        }
        System.out.println("Please select a sort type.\n1.Ascending sort\n2.Descending sort\n3.No sorting");
        String inputNumber = Search.getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
        switch (inputNumber) {
            case "1" -> Search.sortByPriceAscending(mobiles);
            case "2" -> Search.sortByPriceDescending(mobiles);
            default -> {
            }
        }
        Search.showList(scanner, mobiles, customer);
    }

    public List<Mobile> getMobileList() {
        List<Mobile> mobileList = new ArrayList<>();
        for (Good good : Database.getInstance().getGoods()) {
            if ("Mobile".equals(good.getType())) {
                Mobile mobile = (Mobile) good;
                mobileList.add(mobile);
            }
        }
        return mobileList;
    }

    @Override
    public void printFullInformation() {
        System.out.println("Mobile Information:");
        System.out.println("-------------------");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Price: " + getPrice());
        System.out.println("Inventory: " + getNumber());
        System.out.println("Brand: " + getBrand());
        System.out.println("Internal Storage: " + getInternalStorage() + "GB");
        System.out.println("RAM: " + getRam() + "GB");
        System.out.println("Network Type: " + getNetworkType());
        System.out.println("Front camera Resolution" + getFrontResolution());
        System.out.println("Rear camera Resolution" + getMainResolution());
        System.out.println("Seller's Name: " + getSellerName());
        System.out.println("Seller's Province: " + getSellerProvince());
        if (getNumOfComments() != 0) {
            System.out.println("Average Score: " + getAverageScore() + "/5");
        }
        System.out.println("Number of Comments: " + getNumOfComments());
    }

    @Override
    public void createProduct(Scanner scanner, Seller seller) {
        displayProductMenu();
        processProductCreation(scanner, seller);
    }

    private void displayProductMenu() {
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Name\n2.Type\n3.Price\n4.Inventory\n5.Brand\n6.RAM (GB)\n7.Internal Storage (GB)" +
                "\n8.Network Type\n9.Front camera Resolution (MP)\n10.Rear camera Resolution (MP)" +
                "\n11.Confirmation\n12.Back");
    }

    private void processProductCreation(Scanner scanner, Seller seller) {
        while (true) {
            String input = getValidInput(scanner);
            if (!processUserInput(input, scanner, seller)) {
                return;
            }
        }
    }

    private String getValidInput(Scanner scanner) {
        return Search.getValidInput("Please enter a valid number.", "[1-9]|10|11|12", scanner);
    }

    private boolean processUserInput(String input, Scanner scanner, Seller seller) {
        switch (input) {
            case "1" -> updateName(scanner);
            case "2" -> updateType(scanner);
            case "3" -> updatePrice(scanner);
            case "4" -> updateInventory(scanner);
            case "5" -> updateBrand(scanner);
            case "6" -> updateRam(scanner);
            case "7" -> updateInternalStorage(scanner);
            case "8" -> updateNetworkType(scanner);
            case "9" -> updateFrontCameraResolution(scanner);
            case "10" -> updateRearCameraResolution(scanner);
            case "11" -> {
                return confirmAndSaveProduct(seller);
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void updateName(Scanner scanner) {
        setName(readString("name.", "[A-Za-z0-9\\s]+", scanner, getName()));
    }

    private void updateType(Scanner scanner) {
        setType(readString("type.", "(Laptop)|(Mobile)|(Book)", scanner, getType()));
    }

    private void updatePrice(Scanner scanner) {
        setPrice(readString("price.", "[1-9][0-9]*", scanner, getPrice()));
    }

    private void updateInventory(Scanner scanner) {
        setNumber(Integer.parseInt(readString("inventory.", "0|([1-9][0-9]*)", 
                scanner, String.valueOf(getNumber()))));
    }

    private void updateBrand(Scanner scanner) {
        setBrand(readString("brand.", "[A-Za-z0-9 ]+", scanner, getBrand()));
    }

    private void updateRam(Scanner scanner) {
        setRam(readString("RAM.", "[1-9][0-9]*", scanner, getRam()));
    }

    private void updateInternalStorage(Scanner scanner) {
        setInternalStorage(readString("internal storage.", "[1-9][0-9]*", scanner, getInternalStorage()));
    }

    private void updateNetworkType(Scanner scanner) {
        setNetworkType(readString("network type (4G/5G).", "(4G)|(5G)", scanner, getNetworkType()));
    }

    private void updateFrontCameraResolution(Scanner scanner) {
        setFrontResolution(readString("front camera Resolution (MP)", "[1-9][0-9]*", scanner, getFrontResolution()));
    }

    private void updateRearCameraResolution(Scanner scanner) {
        setMainResolution(readString("Rear camera Resolution (MP)", "[1-9][0-9]*", scanner, getMainResolution()));
    }

    private boolean confirmAndSaveProduct(Seller seller) {
        if (!checkGoodInf()) {
            System.out.println("Some field is empty.");
            return true;
        }
        
        setSellerInfo(seller);
        seller.addSalesList(this);
        Database.getInstance().addGood(this);
        System.out.println("Product added successfully.");
        return false;
    }

    public boolean checkGoodInf() {
        return checkField(getName(), "Name") &&
                checkField(getType(), "Type") &&
                checkField(getPrice(), "Price") &&
                checkField(getBrand(), "Brand") &&
                checkField(getRam(), "RAM") &&
                checkField(getInternalStorage(), "Internal Storage") &&
                checkField(getNetworkType(), "Network Type") &&
                checkField(getFrontResolution(), "front camera Resolution") &&
                checkField(mainResolution, "Rear camera Resolution");
    }

    private boolean checkField(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            System.out.println(fieldName + " is required!");
            return false;
        }
        return true;
    }

}