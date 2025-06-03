package ir.ac.kntu;

import ir.ac.kntu.util.Calendar;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Wallet {

    private long balance;
    private List<Transaction> transactions = new ArrayList<>();

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public boolean addTransactions(Transaction transaction) {
        return transactions.add(transaction);
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void showTransaction(Scanner scanner) {
        if (Search.checkEmptyList(getTransactions().isEmpty(), "You have not made any transactions yet.", scanner)) {
            return;
        }
        System.out.println("Do you want transactions to be filtered based on time periods?\n\t1.Yes\n\t2.No");
        String input = Search.getValidInput("Please enter valid number.", "[1-2]", scanner);
        TimeFilter filter = new TimeFilter(false, null, null);
        if ("1".equals(input)) {
            filter = getTimeFilter(scanner);
        }
        if (!filter.isFilter()) {
            printTransactions(getTransactions(), scanner);
            return;
        }
        List<Transaction> transactions = getFilteredTransactions(filter);
        if (transactions.isEmpty()) {
            System.out.println("There is no transaction to show.");
            return;
        }
        printTransactions(transactions, scanner);
    }

    public int printSummeryTransactions(List<Transaction> transactions, int firstIndex) {
        int index = firstIndex;
        for (; index < firstIndex + 10 && index <= transactions.size(); index++) {
            Transaction transaction = transactions.get(index - 1);
            System.out.println(
                    "\t" + index + ". " + (transaction.isCharge() ? "Charge account" : "Take out"));
        }
        return index - 1;
    }

    public void printTransactions(List<Transaction> transactions, Scanner scanner) {
        int firstIndex = 1;
        while (true) {
            System.out.println("Your transactions:");
            int lastIndex = printSummeryTransactions(transactions, firstIndex);
            System.out.println(
                    "To view information about each request, enter the corresponding number.(Enter the letter B to return.)");
            String regex = Search.generateMenuRegex(firstIndex, lastIndex, transactions.size());
            String input = Search.getValidInput("Please enter a valid character.", regex, scanner);
            switch (input) {
                case "P" -> firstIndex -= 10;
                case "N" -> firstIndex += 10;
                case "B" -> {
                    return;
                }
                default -> {
                    int index = Integer.parseInt(input) - 1;
                    System.out.println(transactions.get(index));
                    System.out.println("Enter B to return.");
                    Search.goBack("B", scanner);
                }
            }
        }
    }

    public List<Transaction> getFilteredTransactions(TimeFilter filter) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : getTransactions()) {
            if (!filter.getStart().isBefore(transaction.getDate())) {
                continue;
            }
            if (!filter.getEnd().isAfter(transaction.getDate())) {
                continue;
            }
            transactions.add(transaction);
        }
        return transactions;
    }

    public TimeFilter getTimeFilter(Scanner scanner) {
        while (true) {
            System.out.println("Please enter start data or enter 1 to return.(yyyy-MM-dd)");
            String start = scanner.nextLine();
            if ("1".equals(start)) {
                return new TimeFilter(false, null, null);
            }
            System.out.println("Please enter end data or enter 1 to return.(yyyy-MM-dd)");
            String end = scanner.nextLine().trim();
            if ("1".equals(end)) {
                return new TimeFilter(false, null, null);
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

    public boolean checkTakeOut(long amount) {
        if (amount > getBalance()) {
            System.out.println("Your account balance is insufficient.");
            return false;
        }
        setBalance(getBalance() - amount);
        Transaction transaction = new Transaction(amount, Calendar.now(), false);
        addTransactions(transaction);
        System.out.println("The transaction was successful.");
        return true;
    }

}