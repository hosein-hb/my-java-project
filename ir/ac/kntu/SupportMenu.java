package ir.ac.kntu;

import java.util.*;

public class SupportMenu {

    private Authentication authentication = new Authentication();
    private SupportRequests requests = new SupportRequests();
    private SupportOrders orders = new SupportOrders();

    public void showSupportMenu(Scanner scanner) {
        while (true) {
            System.out.println("To enter each section, enter the corresponding number.");
            System.out.println("\t1.Authentication\n\t2.Requests\n\t3.Orders\n\t4.Back");
            String input = Search.getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
            switch (input) {
                case "1" -> authentication.showList(scanner);
                case "2" -> requests.handleRequests(scanner);
                case "3" -> orders.handleOrders(scanner);
                default -> {
                    return;
                }
            }
        }
    }

}