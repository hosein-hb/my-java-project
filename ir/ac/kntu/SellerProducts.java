package ir.ac.kntu;

import java.util.*;

public class SellerProducts {

    public void handleProducts(Scanner scanner, Seller seller) {
        while (true) {
            System.out.println("1.View Salest List\n2.Add Products To Salest List\n3.Back");
            String input = Search.getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
            switch (input) {
                case "1" -> printSalestList(scanner, seller);
                case "2" -> addProduct(scanner, seller);
                default -> {
                    return;
                }
            }
        }
    }

    public void printSalestList(Scanner scanner, Seller seller) {
        Search.showList(scanner, seller.getSalesList(), seller);
    }

    public void addProduct(Scanner scanner, Seller seller) {
        while (true) {
            System.out.println("Please select the product type.");
            System.out.println("1.Book\t2.Laptop\t3.Mobile\t4.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-4]", scanner);
            Good good;
            switch (input) {
                case "1" -> good = new Book();
                case "2" -> good = new Laptop();
                case "3" -> good = new Mobile();
                default -> {
                    return;
                }
            }
            good.createProduct(scanner, seller);
        }
    }

}
