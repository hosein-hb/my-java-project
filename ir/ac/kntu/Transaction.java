package ir.ac.kntu;

import java.time.Instant;

public class Transaction {

    private long amount;
    private Instant date;
    private boolean isCharge;

    public Transaction(long amount, Instant date, boolean isCharge) {
        this.amount = amount;
        this.date = date;
        this.isCharge = isCharge;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public boolean isCharge() {
        return isCharge;
    }

    public void setCharge(boolean isCharge) {
        this.isCharge = isCharge;
    }

    @Override
    public String toString() {
        return "Transaction amount: " + getAmount()
                + "\nTransaction date: " + getDate()
                + "\nTransaction type: " + (isCharge() ? "Charge account" : "Take out");
    }

}
