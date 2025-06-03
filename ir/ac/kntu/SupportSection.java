package ir.ac.kntu;

import java.util.*;

public class SupportSection {

    private List<Request> requests = new ArrayList<>();

    public void handleSupport(Scanner scanner) {
        while (true) {
            System.out.println("Requests :\n\t1.Submit a request\n\t2.Registered requests\n\t3.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-3]", scanner);
            switch (input) {
                case "1" -> submitRequest(scanner);
                case "2" -> {
                    if (!requests.isEmpty()) {
                        showRegisteredRequest(scanner);
                    } else {
                        System.out.println("There are no registered request.(Enter B to return.)");
                        Search.goBack("B", scanner);
                    }
                }
                default -> {
                    return;
                }
            }
        }
    }

    public void showRegisteredRequest(Scanner scanner) {
        int firstIndex = 1;
        while (true) {
            System.out.println("Registered requests :");
            System.out.println(
                    "To view information about each request, enter the corresponding number.(Enter the letter B to return.)");
            int lastIndex = Request.printSummeryRequests(firstIndex, requests);
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, requests.size());
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    int index = Integer.parseInt(input) - 1;
                    detailsRequest(requests.get(index), scanner);
                }
            }
        }
    }

    public void detailsRequest(Request request, Scanner scanner) {
        request.printFullRequest();
        do {
            System.out.println("Enter 1 to return");
        } while (!"1".equals(scanner.nextLine().trim()));
    }

    public void submitRequest(Scanner scanner) {
        while (true) {
            System.out.println("Please select a request title :");
            System.out.println("\t1.Product quality report");
            System.out.println("\t2.Inconsistency between the order and the product shipped");
            System.out.println("\t3.Setting");
            System.out.println("\t4.Failure to receive order");
            System.out.println("\t5.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-5]", scanner);
            Request request = new Request();
            switch (input) {
                case "1" -> request.setTitle("Product quality");
                case "2" -> request.setTitle("Inconsistency between the order and the product shipped");
                case "3" -> request.setTitle("Setting");
                case "4" -> request.setTitle("Failure to receive order");
                default -> {
                    return;
                }
            }
            if (receiveTextRequest(scanner, request)) {
                return;
            }
        }
    }

    public boolean receiveTextRequest(Scanner scanner, Request request) {
        while (true) {
            System.out.println("Please enter the request text.");
            String requestText = scanner.nextLine();
            while (requestText.isEmpty()) {
                System.out.println("This field cannot be left blank.");
                requestText = scanner.nextLine();
            }
            System.out.println("1.Submit\n2.Edit\n3.Back");
            String input = Search.getValidInput("Please enter a valid number.", "[1-3]", scanner);
            switch (input) {
                case "1" -> {
                    request.setRequestText(requestText);
                    request.setStatus("Registered");
                    requests.add(request);
                    Database.getInstance().addRequest(request);
                    return true;
                }
                case "3" -> {
                    return false;
                }
                default -> {
                }
            }
        }
    }

}