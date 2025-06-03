package ir.ac.kntu;

import java.util.*;
import java.time.Instant;

public abstract class Good {

    private String name;
    private String type;
    private String price;
    private int number;
    private Instant date;
    private String sellerName;
    private String sellerProvince;
    private String sellerId;
    private double averageScore;
    private int numOfComments;
    private String goodId;
    private Set<String> productVotes = new HashSet<>();

    public Good() {
        goodId = GenerateRandomCode.generateUniqueCode(Database.getInstance().getGoodsCode());
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public boolean voting(String phoneNumber) {
        return productVotes.add(phoneNumber);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerProvince() {
        return sellerProvince;
    }

    public void setSellerProvince(String sellerProvince) {
        this.sellerProvince = sellerProvince;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    public String readString(String fieldName, String regex, Scanner scanner, String value) {
        System.out.println("Please enter " + fieldName + "(Enter Back to return.)");
        String input = scanner.nextLine().trim();
        while (true) {
            if ("Back".equals(input)) {
                return value;
            }
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("The entry is invalid.");
            input = scanner.nextLine().trim();
        }
    }

    public boolean readBoolean(String fieldName, Scanner scanner) {
        System.out.println("has " + fieldName + "? (Y/N)");
        String input = Search.getValidInput("The entry is invalid.", "Y|N", scanner);
        return "Y".equals(input);
    }

    public void setSellerInfo(Seller seller) {
        setSellerName(seller.getFirstName() + " " + seller.getLastName());
        setSellerProvince(seller.getProvince());
        setSellerId(seller.getSellerId());
    }

    public abstract void printFullInformation();

    public abstract void search(Filter filter, Scanner scanner, Customer customer);

    public abstract void createProduct(Scanner scanner, Seller seller);

    @Override
    public int hashCode() {
        return Objects.hash(goodId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Good other = (Good) obj;
        if (goodId == null) {
            if (other.goodId != null) {
                return false;
            }
        } else if (!goodId.equals(other.goodId)) {
            return false;
        }
        return true;
    }

}