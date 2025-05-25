package hotel.ui;

import hotel.model.Admin;
import hotel.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminUI {

    private List<Admin> admins;
    private List<Customer> customers;
    private Scanner scanner;

    public AdminUI() {
        scanner = new Scanner(System.in);

        // Hardcoded sample admins
        admins = new ArrayList<>();
        admins.add(new Admin(1, "admin", "admin123", "admin@example.com"));

        // Hardcoded sample customers
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Alice", "alice@example.com", "pass123"));
        customers.add(new Customer(2, "Bob", "bob@example.com", "password"));
    }

    public void run() {
        System.out.println("=== Admin Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (authenticate(email, password)) {
            System.out.println("Login successful!");
            showCustomers();
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private boolean authenticate(String email, String password) {
        for (Admin a : admins) {
            if (a.getEmail().equals(email) && a.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void showCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("=== Customers List ===");
            for (Customer c : customers) {
                System.out.println(c.getName() + " - " + c.getEmail());
            }
        }
    }

    public static void main(String[] args) {
        AdminUI adminUI = new AdminUI();
        adminUI.run();
    }
}
