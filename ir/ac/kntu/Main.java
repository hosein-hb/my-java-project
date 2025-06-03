package ir.ac.kntu;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Support support = new Support("hosein", "1", "2");
        Database.getInstance().addSupport(support);
        try (Scanner scanner = new Scanner(System.in)) {
            GetInfo.userRole(scanner);
        }
    }
}
