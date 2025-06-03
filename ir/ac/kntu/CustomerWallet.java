package ir.ac.kntu;

import java.util.Scanner;

import ir.ac.kntu.util.Calendar;

public class CustomerWallet extends Wallet {

    public void handleWallet(Scanner scanner) {
        while (true) {
            System.out.println("Wallet");
            System.out.println("\t1.Account balance\n\t2.Charging account\n\t3.Previous transactions\n\t4.back");
            String input = Search.getValidInput("Please enter valid number.", "[1-4]", scanner);
            switch (input) {
                case "1" -> {
                    System.out.println("Your current account balance: " + getBalance());
                    System.out.println("Enter B to return.");
                    while (!"B".equals(scanner.nextLine().trim())) {
                        System.out.println("Please enter a valid character.");
                    }
                }
                case "2" -> chargeAccount(scanner);
                case "3" -> showTransaction(scanner);
                default -> {
                    return;
                }
            }
        }
    }

    public void chargeAccount(Scanner scanner) {
        System.out.println("Please enter the charge amount.");
        String input = Search.getValidInput("Please enter an amount.", "\\d+", scanner);
        long amount = Long.parseLong(input);
        setBalance(getBalance() + amount);
        Transaction transaction = new Transaction(amount, Calendar.now(), true);
        addTransactions(transaction);
        System.out.println("The transaction was successful.");
    }

}
