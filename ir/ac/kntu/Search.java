package ir.ac.kntu;

import java.util.*;
import java.util.stream.Collectors;

public class Search {

    public static void searchProduct(Scanner scanner, Customer customer) {
        while (true) {
            System.out.println("Please select an option.\n1.Search by name\n2.Search by type\n3.Back");
            String inputNumber = getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
            switch (inputNumber) {
                case "1" -> searchByName(scanner, customer);
                case "2" -> searchByType(scanner, customer);
                default -> {
                    return;
                }
            }
        }
    }

    public static void searchByName(Scanner scanner, Customer customer) {
        System.out.println("Please enter the product name.");
        String productName = scanner.nextLine().trim().toLowerCase();
        List<Good> filteredProducts = Database.getInstance().getGoods().stream()
                .filter(good -> good.getName().toLowerCase().contains(productName))
                .collect(Collectors.toCollection(ArrayList::new));
        Filter filter = checkFilter(scanner);
        if (filter.isFilter()) {
            priceFilter(filter.getMinPrice(), filter.getMaxPrice(), filteredProducts);
        }
        System.out.println("Please select a sort type.\n1.Ascending sort\n2.Descending sort\n3.No sorting");
        String inputNumber = Search.getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
        switch (inputNumber) {
            case "1" -> Search.sortByPriceAscending(filteredProducts);
            case "2" -> Search.sortByPriceDescending(filteredProducts);
            default -> {
            }
        }
        showList(scanner, filteredProducts, customer);
    }

    public static void searchByType(Scanner scanner, Customer customer) {
        System.out.println("Please choose a category.\n1.Mobile\n2.Laptop\n3.Book\n4.Back");
        String inputNumber = getValidInput("Please enter a number between 1 and 4.", "[1-4]", scanner);
        Filter filter = checkFilter(scanner);
        Good good;
        switch (inputNumber) {
            case "1" -> good = new Mobile();
            case "2" -> good = new Laptop();
            case "3" -> good = new Book();
            default -> {
                return;
            }
        }
        good.search(filter, scanner, customer);
    }

    public static Filter checkFilter(Scanner scanner) {
        System.out.println("Do you want to set a price range?\n1.Yes\n2.No");
        String inputNumber = getValidInput("Please enter a number between 1 and 2.", "[1-2]", scanner);

        if ("2".equals(inputNumber)) {
            return new Filter(false, 0, 0);
        }

        String maxPrice = "0";
        String minPrice = "0";
        while (true) {
            System.out.println("Please enter the maximum price.");
            maxPrice = getValidInput("Please enter a valid price.", "\\d+", scanner);

            System.out.println("Please enter the minimum price.");
            minPrice = getValidInput("Please enter a valid price.", "\\d+", scanner);

            if (Long.parseLong(minPrice) <= Long.parseLong(maxPrice)) {
                break;
            }
            System.out.print("The minimum price must be greater than the maximum price.");
        }
        return new Filter(true, Long.parseLong(maxPrice), Long.parseLong(minPrice));
    }

    public static String getValidInput(String error, String regex, Scanner scanner) {
        String input = scanner.nextLine();
        while (!input.matches(regex)) {
            System.out.println(error);
            input = scanner.nextLine();
        }
        return input;
    }

    public static void sortByPriceAscending(List<? extends Good> goods) {
        goods.sort((m1, m2) -> Long.compare(
                Long.parseLong(m1.getPrice()),
                Long.parseLong(m2.getPrice())));
    }

    public static void sortByPriceDescending(List<? extends Good> goods) {
        goods.sort((m1, m2) -> Long.compare(
                Long.parseLong(m2.getPrice()),
                Long.parseLong(m1.getPrice())));
    }

    public static void priceFilter(long minPrice, long maxPrice, List<? extends Good> goods) {
        goods.removeIf(good -> {
            long price = Long.parseLong(good.getPrice());
            return price < minPrice || price > maxPrice;
        });
    }

    public static int printInformationSummary(int firstIndex, List<? extends Good> goods) {
        int index = firstIndex;
        for (; index < firstIndex + 10 && index <= goods.size(); index++) {
            Good good = goods.get(index - 1);
            System.out.print(index + ".\tName : " + good.getName());
            System.out.print("\tType : " + good.getType());
            System.out.println("\tPrice : " + good.getPrice());
        }
        return index - 1;
    }

    public static void showList(Scanner scanner, List<? extends Good> goods, User user) {
        if (checkEmptyList(goods.isEmpty(), "No product found.", scanner)) {
            return;
        }
        int firstIndex = 1;
        while (true) {
            int lastIndex = printInformationSummary(firstIndex, goods);
            String regex = generateMenuRegex(firstIndex, lastIndex, goods.size());
            System.out.println(
                    "Please enter the product number to view information about each product.(Enter the letter B to return.)");
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    Good good = goods.get(Integer.parseInt(input) - 1);
                    good.printFullInformation();
                    user.operationOnSellectedGood(scanner, good);
                }
            }
        }
    }

    public static String generateMenuRegex(int firstIndex, int lastIndex, int sizeOfList) {
        String regex = "B";
        for (int i = firstIndex; i <= lastIndex; i++) {
            regex += "|" + String.valueOf(i);
        }
        if (firstIndex > 1 && firstIndex + 10 <= sizeOfList) {
            System.out.print("Print P to go previous page and print N to go next page.");
            regex += "|P|N";
        } else if (firstIndex > 1 && firstIndex + 10 > sizeOfList) {
            System.out.print("Print P to go previous page.");
            regex += "|P";
        } else if (firstIndex + 10 <= sizeOfList) {
            System.out.print("Print N to go next page.");
            regex += "|N";
        }
        return regex;
    }

    public static String generateRegex(int lastNumber) {
        String regex = "B";
        for (int i = 1; i <= lastNumber; i++) {
            regex += "|" + String.valueOf(i);
        }
        return regex;
    }

    public static boolean checkEmptyList(boolean isEmpty, String output, Scanner scanner) {
        if (!isEmpty) {
            return false;
        }
        System.out.println(output + "(Enter B to return.)");
        while (!"B".equals(scanner.nextLine().trim())) {
            System.out.println("Please enter a valid character.");
        }
        return true;
    }

    public static void goBack(String backKey, Scanner scanner) {
        while (!backKey.equals(scanner.nextLine().trim())) {
            System.out.println("Please enter a valid character.");
        }
    }

}