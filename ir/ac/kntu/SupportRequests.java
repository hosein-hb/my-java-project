package ir.ac.kntu;

import java.util.*;

public class SupportRequests {

    public void handleRequests(Scanner scanner) {
        List<Request> requests = filterRequests(scanner);
        if (requests == null) {
            return;
        }
        showListOfRequests(requests, scanner);
    }

    public void showListOfRequests(List<Request> requests, Scanner scanner) {
        int firstIndex = 1;
        while (true) {
            if (requests.isEmpty()) {
                System.out.println("There is no request to follow up.(Enter the letter B to return.)");
                Search.goBack("B", scanner);
                return;
            }
            System.out.println("Registered requests :");
            int lastIndex = Request.printSummeryRequests(firstIndex, requests);
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, requests.size());
            System.out.print("Select an option: ");
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
        while (true) {
            request.printFullRequest();
            System.out.println("\n\n1.Answer to request\t\t2.Back");
            String input = Search.getValidInput("Please enter valid number.", "[1-2]", scanner);
            if ("2".equals(input)) {
                return;
            }
            String answer = getAnswer(scanner);
            if ("1".equals(answer)) {
                continue;
            }
            determineStatus(request, scanner, answer);
        }
    }

    public void determineStatus(Request request, Scanner scanner, String answer) {
        System.out.println("Please specify the status of the request.\n1.Following up\n2.Closed\n3.Back");
        String input = Search.getValidInput("Please enter valid number.", "[1-3]", scanner);
        switch (input) {
            case "1" -> {
                request.setSupportAnswer(answer);
                request.setStatus("Following up");
            }
            case "2" -> {
                request.setSupportAnswer(answer);
                request.setStatus("Closed");
                Database.getInstance().getAllRequests().remove(request);
                Database.getInstance().getRequestsCodes().remove(request.getCode());
            }
            default -> {
                return;
            }
        }
    }

    public String getAnswer(Scanner scanner) {
        String answer = "";
        do {
            System.out.println("Please enter the response text.(Enter 1 to return)");
            answer = scanner.nextLine().trim();
        } while (answer.isEmpty());
        return answer;
    }

    public List<Request> filterRequests(Scanner scanner) {
        while (true) {
            List<Request> filterdRequests;
            System.out.println("Filter Requests By:\n1.Status\n2.Title\n3.Show All\n4.Back");
            String input = Search.getValidInput("Please enter valid number.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    filterdRequests = filterByStatus(scanner);
                }
                case "2" -> {
                    filterdRequests = filterByTitle(scanner);
                }
                case "3" -> {
                    filterdRequests = Database.getInstance().getAllRequests();
                }
                default -> {
                    return null;
                }
            }
            if (filterdRequests != null) {
                return filterdRequests;
            }
        }
    }

    public List<Request> filterByStatus(Scanner scanner) {
        String status;
        System.out.println("Request status for filter:\n1.Registered\n2.Following up\n3.Back");
        String input = Search.getValidInput("Please enter valid number.", "[1-3]", scanner);
        switch (input) {
            case "1" -> status = "Registered";
            case "2" -> status = "Following up";
            default -> {
                return null;
            }
        }
        return findRequests(status);
    }

    public List<Request> filterByTitle(Scanner scanner) {
        String title;
        System.out.println("Request status for filter:\n");
        System.out.println("\t1.Product quality");
        System.out.println("\t2.Inconsistency between the order and the product shipped");
        System.out.println("\t3.Setting");
        System.out.println("\t4.Failure to receive order");
        System.out.println("\t5.Back");
        String input = Search.getValidInput("Please enter a valid number.", "[1-5]", scanner);
        switch (input) {
            case "1" -> title = "Product quality";
            case "2" -> title = "Inconsistency between the order and the product shipped";
            case "3" -> title = "Setting";
            case "4" -> title = "Failure to receive order";
            default -> {
                return null;
            }
        }
        return findRequests(title);
    }

    public List<Request> findRequests(String filter) {
        List<Request> filterdRequests = new ArrayList<>();
        for (Request request : Database.getInstance().getAllRequests()) {
            if (request.getStatus().equals(filter)) {
                filterdRequests.add(request);
            }
        }
        return filterdRequests;
    }

}