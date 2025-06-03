package ir.ac.kntu;

import java.util.Scanner;

import ir.ac.kntu.util.Calendar;

public class SellerWallet extends Wallet {

    public void handleWallet(Scanner scanner) {
        while (true) {
            System.out.println("Wallet");
            System.out.println("\t1.Account balance\n\t2.Take out\n\t3.Previous transactions\n\t4.back");
            String input = Search.getValidInput("Please enter valid number.", "[1-4]", scanner);
            switch (input) {
                case "1" -> System.out.println("Your current account balance: " + getBalance());
                case "2" -> takeOut(scanner);
                case "3" -> showTransaction(scanner);
                default -> {
                    return;
                }
            }
        }
    }

    public void takeOut(Scanner scanner) {
        System.out.println("Enter the amount to be withdrawn from the account.");
        String input = Search.getValidInput("Please enter valid number.", "[1-9][0-9]*", scanner);
        long amount = Long.parseLong(input);
        checkTakeOut(amount);
    }

    public void chargeAccount(long amount) {
        setBalance(getBalance() + amount);
        Transaction transaction = new Transaction(amount, Calendar.now(), true);
        addTransactions(transaction);
    }

}
