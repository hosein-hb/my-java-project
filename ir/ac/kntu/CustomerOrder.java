package ir.ac.kntu;

import java.util.*;
import java.time.Instant;

public class CustomerOrder extends Order {

    private Map<Good, Integer> products = new LinkedHashMap<>();
    private long shoppingCosts;
    private String customerName;

    public CustomerOrder(Map<Good, Integer> products, DetailsOfAddress address, Instant date, long shoppingCosts,
            long amount,
            String customerName, String customerEmail) {
        super(address, date, amount, customerEmail);
        this.products = new LinkedHashMap<>(products);
        this.shoppingCosts = shoppingCosts;
        this.customerName = customerName;
    }

    public static int printSummeryOrders(int firstIndex, List<CustomerOrder> orders) {
        int index = firstIndex;
        for (; index < firstIndex + 10 && index <= orders.size(); index++) {
            CustomerOrder order = orders.get(index - 1);
            System.out.println("\t" + index + ". Customer Name: " + order.getCustomerName()
                    + "\tAmount: " + order.getAmount()
                    + "Date: " + order.getDate());
        }
        return index - 1;
    }

    public Map.Entry<Good, Integer> printSummeryOrder(Scanner scanner) {
        int index = 1;
        for (Good good : getProducts().keySet()) {
            System.out.println("\t" + index + ". Product Name: " + good.getName()
                    + "\tSeller Name: " + good.getSellerName()
                    + "\tAddress: " + getAddress()
                    + "\tPrice: " + good.getPrice());
            index++;
        }
        System.out.println("Total Price of products: " + getAmount());
        System.out.println("Postage costs: " + getShoppingCosts());
        System.out.println(
                "\n\nTo view information about each product, enter the corresponding number.(enter B to return.)");
        String regex = Search.generateRegex(products.size());
        String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
        if ("B".equals(input)) {
            return null;
        }
        int selectedIndex = Integer.parseInt(input) - 1;
        List<Map.Entry<Good, Integer>> entries = new ArrayList<>(products.entrySet());
        return entries.get(selectedIndex);
    }

    public long getShoppingCosts() {
        return shoppingCosts;
    }

    public void setShoppingCosts(long shoppingCosts) {
        this.shoppingCosts = shoppingCosts;
    }

    public Map<Good, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Good, Integer> products) {
        this.products.putAll(products);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}