package ir.ac.kntu;

import java.util.*;

public class Orders {

    private List<CustomerOrder> customerOrders = new ArrayList<>();

    public void addOrder(CustomerOrder customerOrder) {
        customerOrders.add(customerOrder);
    }

    public void showOrdersSummery(Scanner scanner, Customer customer) {
        System.out.println("Your Orders");
        int index = 1;
        for (CustomerOrder customerOrder : customerOrders) {
            System.out.println(
                    "\t" + index + ". Date:" + customerOrder.getDate() + "\tAmount: " + customerOrder.getAmount()
                            + "\tAddress" + customerOrder.getAddress());
            index += 1;
        }
        System.out.println("\t" + index + ". Back");
        System.out.println("\nTo view information about each order, enter the corresponding number.");
        String regex = Search.generateRegex(index);
        String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
        int inputNumber = Integer.parseInt(input);
        if (inputNumber == index) {
            return;
        }
        showDetailsOfOrder(customerOrders.get(inputNumber - 1), scanner, customer);
    }

    public void showDetailsOfOrder(CustomerOrder customerOrder, Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Purchase information:");
            int index = 1;
            for (Good good : customerOrder.getProducts().keySet()) {
                System.out.print(index + ".\tName : " + good.getName());
                System.out.print("\tType : " + good.getType());
                System.out.println("\tPrice : " + good.getPrice());
                index++;
            }
            System.out.println("\t" + index + ". Back");
            System.out.println("\nTo view information about each product, enter the corresponding number.");
            String regex = Search.generateRegex(index);
            String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
            int selectedIndex = Integer.parseInt(input) - 1;
            if (selectedIndex + 1 == index) {
                return;
            }
            List<Map.Entry<Good, Integer>> goods = new ArrayList<>(customerOrder.getProducts().entrySet());
            showDetailsOfProduct(goods.get(selectedIndex), scanner, customer);
        }
    }

    public void showDetailsOfProduct(Map.Entry<Good, Integer> good, Scanner scanner, Customer customer) {
        while (true) {
            good.getKey().printFullInformation();
            System.out.println("Order quantity: " + good.getValue());
            System.out.println("1.Rate this product\n2.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-2]", scanner);
            switch (input) {
                case "1" -> checkRating(good.getKey(), scanner, customer);
                default -> {
                    return;
                }
            }

        }
    }

    public void checkRating(Good good, Scanner scanner, Customer customer) {
        if (!good.voting(customer.getPhoneNumber())) {
            System.out.println("You have already rated this product.");
            return;
        }
        System.out.println("Enter a number.(Lowest 1_Highest 5)");
        String regex = "([1-4].[0-9])|(5.0)|[1-5]";
        String input = Search.getValidInput("Please enter a valid number.", regex, scanner);
        double score = Double.parseDouble(input);
        Double newAverageScore = (good.getNumOfComments() * good.getAverageScore() + score)
                / (good.getNumOfComments() + 1);
        good.setNumOfComments(good.getNumOfComments() + 1);
        good.setAverageScore(newAverageScore);
        System.out.println("Thanks for your rating.");
    }

}