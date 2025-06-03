package ir.ac.kntu;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SupportOrders {

    public void handleOrders(Scanner scanner) {
        List<CustomerOrder> customerOrders = filterOrders(scanner);
        if (customerOrders == null) {
            return;
        }
        if (customerOrders.isEmpty()) {
            System.out.println("There are no orders to display.");
            return;
        }
        showListOfOrders(customerOrders, scanner);
    }

    public void showListOfOrders(List<CustomerOrder> customerOrders, Scanner scanner) {
        int firstIndex = 1;
        while (true) {
            System.out.println("Orders:");
            int lastIndex = CustomerOrder.printSummeryOrders(firstIndex, customerOrders);
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, customerOrders.size());
            System.out.println(
                    "To view information about each request, enter the corresponding number.(Enter the letter B to return)");
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    int index = Integer.parseInt(input) - 1;
                    detailsOrder(customerOrders.get(index), scanner);
                }
            }
        }
    }

    public void detailsOrder(CustomerOrder customerOrder, Scanner scanner) {
        while (true) {
            Map.Entry<Good, Integer> good = customerOrder.printSummeryOrder(scanner);
            if (good == null) {
                return;
            }
            good.getKey().printFullInformation();
            System.out.println("Order quantity: " + good.getValue());

            do {
                System.out.println("Enter B to return");
            } while (!"B".equals(scanner.nextLine().trim()));
        }
    }

    public List<CustomerOrder> filterOrders(Scanner scanner) {
        while (true) {
            List<CustomerOrder> filteredOrders;
            System.out.println("Filter Orders By:\n1.Ordering User\n2.Time Frame\n3.Show All\n4.Back");
            String input = Search.getValidInput("Please enter valid number.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    filteredOrders = filterByUser(scanner);
                }
                case "2" -> {
                    filteredOrders = filterByTime(scanner);
                }
                case "3" -> {
                    filteredOrders = Database.getInstance().getOrders();
                }
                default -> {
                    return null;
                }
            }
            if (filteredOrders != null) {
                return filteredOrders;
            }
        }
    }

    public List<CustomerOrder> filterByTime(Scanner scanner) {
        TimeFilter timeFilter = getTimeFilter(scanner);
        if (timeFilter == null) {
            return null;
        }
        List<CustomerOrder> filteredOrders = new ArrayList<>();
        for (CustomerOrder order : Database.getInstance().getOrders()) {
            if (!timeFilter.getStart().isBefore(order.getDate())) {
                continue;
            }
            if (!timeFilter.getEnd().isAfter(order.getDate())) {
                continue;
            }
            filteredOrders.add(order);
        }
        return filteredOrders;
    }

    public TimeFilter getTimeFilter(Scanner scanner) {
        while (true) {
            System.out.println("Please enter start data or enter 1 to return.(yyyy-MM-dd)");
            String start = scanner.nextLine();
            if ("1".equals(start)) {
                return null;
            }
            System.out.println("Please enter end data or enter 1 to return.(yyyy-MM-dd)");
            String end = scanner.nextLine();
            if ("1".equals(end)) {
                return null;
            }
            try {
                Instant startDate = LocalDate.parse(start).atStartOfDay(ZoneId.systemDefault()).toInstant();
                Instant endDate = LocalDate.parse(end).plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
                if (startDate.isAfter(endDate)) {
                    System.out.println("Start date cannot be after end date");
                    continue;
                }
                return new TimeFilter(true, startDate, endDate);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format");
            }
        }
    }

    public List<CustomerOrder> filterByUser(Scanner scanner) {
        List<CustomerOrder> filteredOrders = new ArrayList<>();
        System.out.println("Please enter the user's email.(enter 1 to return.)");
        String email = scanner.nextLine();
        while (checkEmailValidation(email)) {
            if ("1".equals(email)) {
                return null;
            }
            email = scanner.nextLine();
        }
        for (CustomerOrder customerOrder : Database.getInstance().getOrders()) {
            if (customerOrder.getCustomerEmail().equals(email)) {
                filteredOrders.add(customerOrder);
            }
        }
        return filteredOrders;
    }

    public boolean checkEmailValidation(String email) {
        if (!email.matches("[A-Za-z0-9_.]+@gmail\\.com")) {
            System.out.println("Email format is invalid. Please try again.");
            return false;
        } else if (!isEmailExist(email)) {
            System.out.println("This email is not exists.");
            return false;
        }
        return true;
    }

    public boolean isEmailExist(String email) {
        for (User user : Database.getInstance().getUsers()) {
            if (!"Customers".equals(user.getRole())) {
                continue;
            }
            Customer customer = (Customer) user;
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

}
