package ir.ac.kntu;

import java.util.*;

public class SellerMenu {

    private SellerProducts products = new SellerProducts();

    public void showSellerMenu(Seller seller, Scanner scanner) {
        while (true) {
            System.out.println("To enter each section, enter the corresponding number.");
            System.out.println("\t1.Products\n\t2.Wallet\n\t3.Orders\n\t4.Back");
            String input = Search.getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
            switch (input) {
                case "1" -> products.handleProducts(scanner, seller);
                case "2" -> seller.getWallet().handleWallet(scanner);
                case "3" -> SellerOrder.printOrders(seller.getSelledProducts(), scanner);
                default -> {
                    return;
                }
            }
        }
    }

}
