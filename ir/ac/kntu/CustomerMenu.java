package ir.ac.kntu;

import java.util.*;

public class CustomerMenu {

    public void showCustomerMenu(Customer customer, Scanner scanner) {

        while (true) {
            System.out.println("\nTo enter each section, enter the corresponding number.");
            System.out.println(
                    "1.Search\n2.Shopping cart\n3.Addresses\n4.wallet\n5.Orders\n6.Settings\n7.Support\n8.Back");
            String input = Search.getValidInput("Please enter a number between 1 and 8.", "[1-8]", scanner);
            switch (input) {
                case "1" -> Search.searchProduct(scanner, customer);
                case "2" -> customer.getShoppingCart().handleShopping(scanner, customer);
                case "3" -> customer.getAdress().handleAddress(scanner);
                case "4" -> customer.getWallet().handleWallet(scanner);
                case "5" -> customer.getOrders().showOrdersSummery(scanner, customer);
                case "6" -> customer.editProfile(scanner, customer);
                case "7" -> customer.getSupportSection().handleSupport(scanner);
                default -> {
                    return;
                }
            }
        }

    }

}
