package ir.ac.kntu;

import java.util.*;

public class Book extends Good {

    private String writer;
    private String numOfPages;
    private String genre;
    private String ageGroup;
    private String isbn;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(String numOfPages) {
        this.numOfPages = numOfPages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public void search(Filter filter, Scanner scanner, Customer customer) {
        List<Book> books = getBookList();
        if (filter.isFilter()) {
            Search.priceFilter(filter.getMinPrice(), filter.getMaxPrice(), books);
        }
        System.out.println("Please select a sort type.\n1.Ascending sort\n2.Descending sort\n3.No sorting");
        String inputNumber = Search.getValidInput("Please enter a number between 1 and 3.", "[1-3]", scanner);
        switch (inputNumber) {
            case "1" -> Search.sortByPriceAscending(books);
            case "2" -> Search.sortByPriceDescending(books);
            default -> {
            }
        }
        Search.showList(scanner, books, customer);
    }

    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        for (Good good : Database.getInstance().getGoods()) {
            if ("Book".equals(good.getType())) {
                Book book = (Book) good;
                bookList.add(book);
            }
        }
        return bookList;
    }

    @Override
    public void printFullInformation() {
        System.out.println("Book Information:");
        System.out.println("----------------");
        System.out.println("Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Price: " + getPrice());
        System.out.println("Inventory: " + getNumber());
        System.out.println("Writer: " + getWriter());
        System.out.println("Number of Pages: " + getNumOfPages());
        System.out.println("Genre: " + getGenre());
        System.out.println("Age Group: " + getAgeGroup());
        System.out.println("ISBN: " + getIsbn());
        System.out.println("Seller's Name: " + getSellerName());
        System.out.println("Seller's Province: " + getSellerProvince());
        if (getNumOfComments() != 0) {
            System.out.println("Average Score: " + getAverageScore() + "/5");
        }
        System.out.println("Number of Comments: " + getNumOfComments());
    }

    @Override
    public void createProduct(Scanner scanner, Seller seller) {
        displayMenu();
        processUserInput(scanner, seller);
    }

    private void displayMenu() {
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Name\n2.Type\n3.Price\n4.Inventory\n5.Writer\n6.Number of Pages\n7.Genre" +
                "\n8.Age Group\n9.ISBN\n10.Confirmation\n11.Back");
    }

    private void processUserInput(Scanner scanner, Seller seller) {
        while (true) {
            String input = Search.getValidInput("Please enter a valid number.", "[1-9]|10|11", scanner);
            if (!handleUserChoice(input, scanner, seller)) {
                return;
            }
        }
    }

    private boolean handleUserChoice(String input, Scanner scanner, Seller seller) {
        switch (input) {
            case "1" -> handleName(scanner);
            case "2" -> handleType(scanner);
            case "3" -> handlePrice(scanner);
            case "4" -> handleInventory(scanner);
            case "5" -> handleWriter(scanner);
            case "6" -> handlePages(scanner);
            case "7" -> handleGenre(scanner);
            case "8" -> handleAgeGroup(scanner);
            case "9" -> handleIsbn(scanner);
            case "10" -> {
                return handleConfirmation(seller);
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private void handleName(Scanner scanner) {
        setName(readString("name.", "[A-Za-z0-9\\s]+", scanner, getName()));
    }

    private void handleType(Scanner scanner) {
        setType(readString("type.", "(Laptop)|(Mobile)|(Book)", scanner, getType()));
    }

    private void handlePrice(Scanner scanner) {
        setPrice(readString("price.", "[1-9][0-9]*", scanner, getPrice()));
    }

    private void handleInventory(Scanner scanner) {
        setNumber(Integer.parseInt(readString("inventory.", "0|([1-9][0-9]*)", 
                scanner, String.valueOf(getNumber()))));
    }

    private void handleWriter(Scanner scanner) {
        setWriter(readString("writer.", "[A-Za-z]+", scanner, getWriter()));
    }

    private void handlePages(Scanner scanner) {
        setNumOfPages(readString("number of pages.", "[1-9][0-9]*", scanner, getNumOfPages()));
    }

    private void handleGenre(Scanner scanner) {
        setGenre(readString("genre.", "[A-Za-z0-9]+", scanner, getGenre()));
    }

    private void handleAgeGroup(Scanner scanner) {
        setAgeGroup(readString("age group (children/teenager/adult).", 
                "(children)|(teenager)|(adult)", scanner, getAgeGroup()));
    }

    private void handleIsbn(Scanner scanner) {
        setIsbn(readString("ISBN.", "[1-9][0-9]*", scanner, getIsbn()));
    }

    private boolean handleConfirmation(Seller seller) {
        if (!checkGoodInf()) {
            System.out.println("Some field is empty.");
            return true;
        }
        setSellerInfo(seller);
        seller.addSalesList(this);
        Database.getInstance().addGood(this);
        System.out.println("Product added successfully.");
        return false;
    }

    public boolean checkGoodInf() {
        return checkField(getName(), "Name") &&
                checkField(getType(), "Type") &&
                checkField(getPrice(), "Price") &&
                checkField(getWriter(), "Writer") &&
                checkField(getNumOfPages(), "Number of Pages") &&
                checkField(getGenre(), "Genre") &&
                checkField(getAgeGroup(), "Age Group") &&
                checkField(getIsbn(), "ISBN");
    }

    private boolean checkField(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            System.out.println(fieldName + " is required!");
            return false;
        }
        return true;
    }

}
