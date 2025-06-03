package ir.ac.kntu;

import java.util.*;

public class Laptop extends Digital {

    private String gpu;
    private boolean bluetooth;
    private boolean webcam;

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isWebcam() {
        return webcam;
    }

    public void setWebcam(boolean webcam) {
        this.webcam = webcam;
    }

    @Override
    public void search(Filter filter, Scanner scanner, Customer customer) {
        List<Laptop> laptops = getLaptopList();
        if (filter.isFilter()) {
            Search.priceFilter(filter.getMinPrice(), filter.getMaxPrice(), laptops);
        }
        System.out.println("Please select a sort type.\n1.Ascending sort\n2.Descending sort\n3.No sorting");
        String inputNumber = Search.getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
        switch (inputNumber) {
            case "1" -> Search.sortByPriceAscending(laptops);
            case "2" -> Search.sortByPriceDescending(laptops);
            default -> {
            }
        }
        Search.showList(scanner, laptops, customer);
    }

    public List<Laptop> getLaptopList() {
        List<Laptop> laptopList = new ArrayList<>();
        for (Good good : Database.getInstance().getGoods()) {
            if ("Laptop".equals(good.getType())) {
                Laptop laptop = (Laptop) good;
                laptopList.add(laptop);
            }
        }
        return laptopList;
    }

    @Override
    public void printFullInformation() {
        System.out.println("Laptop Information:");
        System.out.println("-------------------");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Price: " + getPrice());
        System.out.println("Inventory: " + getNumber());
        System.out.println("Brand: " + getBrand());
        System.out.println("Internal Storage: " + getInternalStorage() + "GB");
        System.out.println("RAM: " + getRam() + "GB");
        System.out.println("GPU: " + getGpu());
        System.out.println("Webcam: " + (isWebcam() ? "has" : "has not"));
        System.out.println("Bluetooth: " + (isBluetooth() ? "has" : "has not"));
        System.out.println("Seller's Name: " + getSellerName());
        System.out.println("Seller's Province: " + getSellerProvince());
        if (getNumOfComments() != 0) {
            System.out.println("Average Score: " + getAverageScore() + "/5");
        }
        System.out.println("Number of Comments: " + getNumOfComments());
    }

    @Override
    public void createProduct(Scanner scanner, Seller seller) {
        displayProductCreationMenu();
        processProductCreation(scanner, seller);
    }

    private void displayProductCreationMenu() {
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Name\n2.Type\n3.Price\n4.Inventory\n5.Brand\n6.RAM (GB)\n7.Internal Storage (GB)" +
                "\n8.GPU\n9.Webcam\n10.Bluetooth\n11.Confirmation\n12.Back");
    }

    private void processProductCreation(Scanner scanner, Seller seller) {
        while (true) {
            String input = getValidMenuInput(scanner);
            if (!processUserSelection(input, scanner, seller)) {
                return;
            }
        }
    }

    private String getValidMenuInput(Scanner scanner) {
        return Search.getValidInput("Please enter a valid number.", "[1-9]|10|11|12", scanner);
    }

    private boolean processUserSelection(String input, Scanner scanner, Seller seller) {
        switch (input) {
            case "1" -> updateProductName(scanner);
            case "2" -> updateProductType(scanner);
            case "3" -> updateProductPrice(scanner);
            case "4" -> updateInventoryCount(scanner);
            case "5" -> updateProductBrand(scanner);
            case "6" -> updateRamCapacity(scanner);
            case "7" -> updateStorageCapacity(scanner);
            case "8" -> updateGpuSpecification(scanner);
            case "9" -> updateWebcamStatus(scanner);
            case "10" -> updateBluetoothStatus(scanner);
            case "11" -> {
                return confirmAndSaveProduct(seller);
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void updateProductName(Scanner scanner) {
        setName(readString("name.", "[A-Za-z0-9\\s]+", scanner, getName()));
    }

    private void updateProductType(Scanner scanner) {
        setType(readString("type.", "(Laptop)|(Mobile)|(Book)", scanner, getType()));
    }

    private void updateProductPrice(Scanner scanner) {
        setPrice(readString("price.", "[1-9][0-9]*", scanner, getPrice()));
    }

    private void updateInventoryCount(Scanner scanner) {
        setNumber(Integer.parseInt(readString("inventory.", "0|([1-9][0-9]*)", 
                scanner, String.valueOf(getNumber()))));
    }

    private void updateProductBrand(Scanner scanner) {
        setBrand(readString("brand.", "[A-Za-z0-9 ]+", scanner, getBrand()));
    }

    private void updateRamCapacity(Scanner scanner) {
        setRam(readString("RAM.", "[1-9][0-9]*", scanner, getRam()));
    }

    private void updateStorageCapacity(Scanner scanner) {
        setInternalStorage(readString("internal storage.", "[1-9][0-9]*", scanner, getInternalStorage()));
    }

    private void updateGpuSpecification(Scanner scanner) {
        setGpu(readString("GPU.", "[A-Za-z0-9 ]+", scanner, getGpu()));
    }

    private void updateWebcamStatus(Scanner scanner) {
        setWebcam(readBoolean("webcam", scanner));
    }

    private void updateBluetoothStatus(Scanner scanner) {
        setBluetooth(readBoolean("Bluetooth", scanner));
    }

    private boolean confirmAndSaveProduct(Seller seller) {
        if (!checkGoodInf()) {
            System.out.println("Some required fields are empty.");
            return true;
        }
        
        completeProductRegistration(seller);
        System.out.println("Product added successfully.");
        return false;
    }

    private void completeProductRegistration(Seller seller) {
        setSellerInfo(seller);
        seller.addSalesList(this);
        Database.getInstance().addGood(this);
    }

    public boolean checkGoodInf() {
        return checkField(getName(), "Name") &&
                checkField(getType(), "Type") &&
                checkField(getPrice(), "Price") &&
                checkField(getBrand(), "Brand") &&
                checkField(getRam(), "RAM") &&
                checkField(getInternalStorage(), "Internal Storage") &&
                checkField(getGpu(), "GPU");
    }

    private boolean checkField(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            System.out.println(fieldName + " is required!");
            return false;
        }
        return true;
    }
}