package ir.ac.kntu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authentication {

    public void showList(Scanner scanner) {
        int firstIndex = 1;
        while (true) {
            List<Seller> watingList = getWaitingList(Database.getInstance().getWaitingList());
            if (Search.checkEmptyList(watingList.isEmpty(), "There is no request for authentication.", scanner)) {
                return;
            }
            int lastIndex = showSellerRequests(firstIndex);
            System.out.println(
                    "To view information about each seller, enter the corresponding number.(Enter the letter B to return.)");
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, lastIndex);
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    int index = Integer.parseInt(input) - 1;
                    watingList.get(index).printFullInformation();
                    answerToRequest(index, scanner);
                }
            }
        }
    }

    public List<Seller> getWaitingList(List<Seller> sellers) {
        List<Seller> waitingSellers = new ArrayList<>();
        for (Seller seller : sellers) {
            if (!seller.isChecked()) {
                waitingSellers.add(seller);
            }
        }
        return waitingSellers;
    }

    public int showSellerRequests(int firstIndex) {
        List<Seller> watingList = Database.getInstance().getWaitingList();
        int index = firstIndex;
        System.out.println("Authentication requests:");
        for (; index < firstIndex + 10 && index <= watingList.size(); index++) {
            Seller seller = watingList.get(index - 1);
            System.out.println(index + "." + seller.getFirstName() + " " + seller.getLastName());
        }
        return index - 1;
    }

    public void answerToRequest(int index, Scanner scanner) {
        List<Seller> watingList = Database.getInstance().getWaitingList();
        System.out.println("1.Confirm membership request.");
        System.out.println("2.Membership request rejection.");
        System.out.println("3.Back");
        String input = Search.getValidInput("Please enter a valid number.", "[1-3]", scanner);
        switch (input) {
            case "1" -> {
                Database.getInstance().addUser(watingList.get(index));
                watingList.remove(index);
                System.out.println("User information confirmed.(Enter B to return.)");
                Search.goBack("B", scanner);
            }
            case "2" -> {
                System.out.println("Reason for rejection of membership application");
                watingList.get(index).setSupportAnswer(scanner.nextLine());
                watingList.get(index).setChecked(true);
                System.out.println("User registration request rejected.(Enter B to return.)");
                Search.goBack("B", scanner);
            }
            default -> {
                return;
            }
        }
    }

}