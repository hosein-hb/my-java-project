package ir.ac.kntu;

import java.util.*;

public class Addresses {

    private List<DetailsOfAddress> addresses = new ArrayList<>();

    public List<DetailsOfAddress> getAddresses() {
        return addresses;
    }

    public void handleAddress(Scanner scanner) {
        while (true) {
            System.out.println("Address");
            System.out.println("\t1.Saved addresses\n\t2.Add address\n\t3.Remove address\n\t4.Edit address\n\t5.Back");
            String input = Search.getValidInput("Please enter valid number.", "[1-5]", scanner);
            switch (input) {
                case "1" -> showAddresses();
                case "2" -> addAddress(scanner);
                case "3" -> removeAddress(scanner);
                case "4" -> editAddress(scanner);
                default -> {
                    return;
                }
            }
        }
    }

    public boolean addAddress(Scanner scanner) {
        DetailsOfAddress address = new DetailsOfAddress();
        System.out.println("To complete the information for each section, enter the corresponding number.");
        System.out.println("1.Title\n2.Province\n3.City\n4.Description\n5.Confirmation\n6.Back");
        while (true) {
            System.out.print("Select an option: ");
            String input = Search.getValidInput("Please enter valid number.", "[1-6]", scanner);
            switch (input) {
                case "1" -> address.readTitle(scanner);
                case "2" -> address.readProvince(scanner);
                case "3" -> address.readCity(scanner);
                case "4" -> address.readDescription(scanner);
                case "5" -> {
                    if (!address.thereEmptyField()) {
                        addresses.add(address);
                        System.out.println("The address has been successfully added to your address list.");
                        return true;
                    }
                }
                default -> {
                    return false;
                }
            }
        }
    }

    public void removeAddress(Scanner scanner) {
        int lastIndex = showAddresses();
        if (lastIndex == 0) {
            return;
        }
        System.out.println("Please enter the address number you want to delete.");
        String regex = "[1-" + String.valueOf(lastIndex) + "]";
        String input = Search.getValidInput("Please enter valid number.", regex, scanner);
        int inputNumber = Integer.parseInt(input);
        if (inputNumber == lastIndex) {
            return;
        }
        addresses.remove(inputNumber - 1);
    }

    public void editAddress(Scanner scanner) {
        int lastIndex = showAddresses();
        if (lastIndex == 0) {
            return;
        }
        System.out.println("Please enter the address number you want to edit.");
        String regex = "[1-" + String.valueOf(lastIndex) + "]";
        String input = Search.getValidInput("Please enter valid number.", regex, scanner);
        int inputNumber = Integer.parseInt(input);
        if (inputNumber == lastIndex) {
            return;
        }
        DetailsOfAddress address = addresses.get(inputNumber - 1);
        while (true) {
            System.out.println("To edit the information for each section, enter the corresponding number.");
            System.out.println("1.Title\n2.Province\n3.City\n4.Description\n5.Back");
            input = Search.getValidInput("Please enter a number between 1 and 5.", "[1-5]", scanner);
            inputNumber = Integer.parseInt(input);
            switch (inputNumber) {
                case 1 -> address.readTitle(scanner);
                case 2 -> address.readProvince(scanner);
                case 3 -> address.readCity(scanner);
                case 4 -> address.readDescription(scanner);
                default -> {
                    return;
                }
            }
        }
    }

    public int showAddresses() {
        if (addresses.isEmpty()) {
            System.out.println("You have not added an address yet.");
            return 0;
        }
        System.out.println("Your address list:");
        int index = 0;
        for (DetailsOfAddress address : addresses) {
            index += 1;
            System.out.println(index + ". " + address.getTitle() + " \\ " + address.getProvince() + " \\ "
                    + address.getCity() + " \\ "
                    + address.getDescription());
        }
        int lastIndex = addresses.size() + 1;
        System.out.println(lastIndex + ". Back");
        return lastIndex;
    }

}
