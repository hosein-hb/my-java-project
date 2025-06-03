package ir.ac.kntu;

import java.time.Instant;
import java.util.*;
import ir.ac.kntu.util.Calendar;

public class ShoppingCart {

    private Map<Good, Integer> shoppingList = new LinkedHashMap<>();
    private DetailsOfAddress mainAddress = new DetailsOfAddress();

    public Map<Good, Integer> getShoppingList() {
        return shoppingList;
    }

    public void addShoppingList(Good good, int num) {
        shoppingList.put(good, num);
    }

    public void handleShopping(Scanner scanner, Customer customer) {
        while (true) {
            if (shoppingList.isEmpty()) {
                System.out.println("\nYour shopping cart is empty.(Enter B to return.)");
                Search.goBack("B", scanner);
                return;
            }
            showShoppingList();
            System.out.println("To enter each section, enter the corresponding number.");
            System.out.println("A.View complete product information.\nB.Place order\nC.Back");
            String input = Search.getValidInput("Please enter a valid character.", "[A-C]", scanner);
            switch (input) {
                case "A" -> showProductInformation(scanner);
                case "B" -> placeOrder(scanner, customer);
                default -> {
                    return;
                }
            }
        }
    }

    public long getTotalPrice() {
        long sum = 0;
        for (Map.Entry<Good, Integer> entry : shoppingList.entrySet()) {
            sum += Long.parseLong(entry.getKey().getPrice()) * entry.getValue();
        }
        return sum;
    }

    public void showShoppingList() {
        System.out.println("Your shopping list:");
        int index = 1;
        for (Good good : shoppingList.keySet()) {
            System.out.print(index + ".\tName : " + good.getName());
            System.out.print("\tType : " + good.getType());
            System.out.println("\tPrice : " + good.getPrice());
            index++;
        }
        System.out.println("Total Price of products: " + getTotalPrice());
    }

    public void showProductInformation(Scanner scanner) {
        System.out
                .println("Please enter the product number to view information about each product.(Enter B to return)");
        String regex = Search.generateRegex(shoppingList.size());
        String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
        int selectedIndex = Integer.parseInt(input) - 1;
        List<Map.Entry<Good, Integer>> goods = new ArrayList<>(shoppingList.entrySet());
        Map.Entry<Good, Integer> good = goods.get(selectedIndex);
        good.getKey().printFullInformation();
        System.out.println("Order quantity: " + good.getValue());
        System.out.println("1.Remove product from cart.\n2.Change order quantity\n3.Back");
        input = Search.getValidInput("Please enter a valid number.", "[1-3]", scanner);
        switch (input) {
            case "1" -> shoppingList.remove(good.getKey());
            case "2" -> shoppingList.put(good.getKey(), orderQuantity(scanner, good.getValue()));
            default -> {
                return;
            }
        }
    }

    public int orderQuantity(Scanner scanner, int currentNum) {
        System.out.println("Please enter the order quantity. (Enter B to return)");
        String num = scanner.nextLine().trim();
        while (true) {
            if (num.matches("0|[1-9][0-9]*")) {
                return Integer.parseInt(num);
            }
            if ("B".equals(num)) {
                return currentNum;
            }
            System.out.println("Please enter a valid input.");
            num = scanner.nextLine().trim();
        }
    }

    public void placeOrder(Scanner scanner, Customer customer) {
        if (!setMainAddress(scanner, customer)) {
            return;
        }
        if (!checkNumOfGoods()) {
            return;
        }
        long shoppingCosts = calculatingShoppingCosts();
        long totalPrice = getTotalPrice() + shoppingCosts;
        showShoppingList();
        System.out.println("Postage costs: " + shoppingCosts);
        System.out.println("total : " + totalPrice);
        System.out.println("\n\n1.Pay with wallet\n2.Back");
        String input = Search.getValidInput("Please enter a valid number.", "[1-2]", scanner);
        if ("2".equals(input)) {
            return;
        }
        if (customer.getWallet().checkTakeOut(totalPrice)) {
            registerOrderInfo(customer, shoppingCosts, totalPrice);
        }
    }

    public void registerOrderInfo(Customer customer, long shoppingCosts, long totalPrice) {
        String customerName = customer.getFirstName() + " " + customer.getLastName();
        Instant transactionTime = Calendar.now();
        CustomerOrder customerOrder = new CustomerOrder(shoppingList, mainAddress, transactionTime, shoppingCosts,
                totalPrice, customerName,
                customer.getEmail());
        customer.getOrders().addOrder(customerOrder);
        Database.getInstance().addOrder(customerOrder);
        payToSeller(customer, transactionTime);
        shoppingList.clear();
    }

    public void payToSeller(Customer customer, Instant transactionTime) {
        for (Map.Entry<Good, Integer> good : shoppingList.entrySet()) {
            Seller seller = findSeller(good.getKey().getSellerId());
            long goodPrice = Long.parseLong(good.getKey().getPrice()) * good.getValue();
            long price = Math.round(goodPrice * 0.9);
            seller.getWallet().chargeAccount(price);
            SellerOrder sellerOrder = new SellerOrder(mainAddress, transactionTime, price, customer.getEmail(), good);
            good.getKey().setNumber(good.getKey().getNumber() - good.getValue());
            seller.addSelledProduct(sellerOrder);
        }
    }

    public Seller findSeller(String inputId) {
        for (User user : Database.getInstance().getUsers()) {
            if (!"Seller".equals(user.getRole())) {
                continue;
            }
            Seller seller = (Seller) user;
            if (seller.getSellerId().equals(inputId)) {
                return seller;
            }
        }
        return null;
    }

    public boolean setMainAddress(Scanner scanner, Customer customer) {
        List<DetailsOfAddress> addresses = customer.getAdress().getAddresses();
        boolean isSelectAddress = false;
        while (!isSelectAddress) {
            System.out.println("Address :");
            System.out.println("\t1.Choose from registered addresses.\n\t2.Add address\n\t3.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-3]", scanner);
            switch (input) {
                case "1" -> {
                    isSelectAddress = chooseAddress(scanner, customer, mainAddress);
                    if (isSelectAddress) {
                        return true;
                    }
                }
                case "2" -> {
                    isSelectAddress = customer.getAdress().addAddress(scanner);
                    if (isSelectAddress) {
                        mainAddress.copyAddress(addresses.get(addresses.size() - 1));
                        return true;
                    }
                }
                default -> {
                    isSelectAddress = true;
                }
            }
        }
        return false;
    }

    public boolean chooseAddress(Scanner scanner, Customer customer, DetailsOfAddress mainAddress) {
        int lastNumber = customer.getAdress().showAddresses();
        if (lastNumber == 0) {
            return false;
        }
        System.out.println("Please choose address.");
        String regex = "[1-" + String.valueOf(lastNumber) + "]";
        String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
        int inputNumber = Integer.parseInt(input);
        if (inputNumber == lastNumber) {
            return false;
        }
        DetailsOfAddress newAddress = customer.getAdress().getAddresses().get(inputNumber - 1);
        mainAddress.copyAddress(newAddress);
        return true;
    }

    public long calculatingShoppingCosts() {
        for (Good good : shoppingList.keySet()) {
            if (mainAddress.getProvince().equals(good.getSellerProvince())) {
                return 200000;
            }
        }
        return 600000;
    }

    public boolean checkNumOfGoods() {
        for (Map.Entry<Good, Integer> entry : shoppingList.entrySet()) {
            if (entry.getKey().getNumber() < entry.getValue()) {
                System.out.println("The number of " + entry.getKey().getName() + " in the store is "
                        + entry.getKey().getNumber() + " . To place an order, change the quantity of this product.");
                return false;
            }
        }
        return true;
    }

}
