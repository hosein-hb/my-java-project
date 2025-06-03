package ir.ac.kntu;

import java.time.Instant;
import java.util.*;

public class SellerOrder extends Order {

    private Good good;
    private int quantity;

    public SellerOrder(DetailsOfAddress address, Instant date, long amount, String customerEmail,
            Map.Entry<Good, Integer> good) {
        super(address, date, amount, customerEmail);
        this.good = good.getKey();
        this.quantity = good.getValue();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public static void printOrders(List<SellerOrder> orders, Scanner scanner) {
        if (Search.checkEmptyList(orders.isEmpty(), "Order not registered.", scanner)) {
            return;
        }
        int firstIndex = 1;
        while (true) {
            int lastIndex = printInformationSummary(firstIndex, orders);
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, orders.size());
            System.out.println(
                    "Please enter the order number to view information about each order.(Enter the letter B to return.)");
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    SellerOrder order = orders.get(Integer.parseInt(input) - 1);
                    order.printFullOrder(scanner);
                }
            }
        }
    }

    public static int printInformationSummary(int firstIndex, List<SellerOrder> orders) {
        int index = firstIndex;
        for (; index < firstIndex + 10 && index <= orders.size(); index++) {
            SellerOrder order = orders.get(index - 1);
            System.out.print(index + ".\tProduct Name: " + order.getGood().getName());
            System.out.print("\tOrder quantity: " + order.getQuantity());
            System.out.println("\tDate: " + order.getDate());
        }
        return index - 1;
    }

    public void printFullOrder(Scanner scanner) {
        getGood().printFullInformation();
        System.out.println("Customer Email:" + getCustomerEmail());
        System.out.println("Date:" + getDate());
        System.out.println("Enter 1 to return.");
        Search.goBack("1", scanner);
    }

}
