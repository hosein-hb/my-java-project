package ir.ac.kntu;

import java.time.Instant;

public class Order {

    private DetailsOfAddress address;
    private Instant date;
    private long amount;
    private String customerEmail;

    public Order(DetailsOfAddress address, Instant date, long amount, String customerEmail) {
        this.address = address;
        this.date = date;
        this.amount = amount;
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public DetailsOfAddress getAddress() {
        return address;
    }

    public void setAddress(DetailsOfAddress address) {
        this.address = address;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
