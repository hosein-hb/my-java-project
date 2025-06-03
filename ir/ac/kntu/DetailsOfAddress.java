package ir.ac.kntu;

import java.util.Scanner;

public class DetailsOfAddress {

    private String title;
    private String province;
    private String city;
    private String description;

    public DetailsOfAddress() {
        title = "";
        province = "";
        city = "";
        description = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void readTitle(Scanner scanner) {
        setTitle("");
        String regex = "[a-zA-Z\s]+";
        while (getTitle().isEmpty() || !getTitle().matches(regex)) {
            System.out.println("Please enter title.(Home, Workplace, ...)");
            setTitle(scanner.nextLine().trim());
        }
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void readProvince(Scanner scanner) {
        setProvince("");
        String regex = "[a-zA-Z\s]+";
        while (getProvince().isEmpty() || !getProvince().matches(regex)) {
            System.out.println("Please enter province.");
            setProvince(scanner.nextLine().trim());
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void readCity(Scanner scanner) {
        setCity("");
        String regex = "[a-zA-Z\s]+";
        while (getCity().isEmpty() || !getCity().matches(regex)) {
            System.out.println("Please enter city.");
            setCity(scanner.nextLine().trim());
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void readDescription(Scanner scanner) {
        System.out.println("Please enter description");
        setDescription(scanner.nextLine().trim());
    }

    public boolean thereEmptyField() {
        return getTitle().isEmpty()
                || getProvince().isEmpty()
                || getCity().isEmpty();
    }

    public void copyAddress(DetailsOfAddress newAddress) {
        setTitle(newAddress.getTitle());
        setProvince(newAddress.getProvince());
        setCity(newAddress.getCity());
        setDescription(newAddress.getDescription());
    }

    @Override
    public String toString() {
        if (description == null || description.isEmpty()) {
            return "Details Of Address: " + title + "/ " + province + "/ " + city;
        }
        return "Details Of Address: " + title + "/ " + province + "/ " + city + "/ "
                + description;
    }

}