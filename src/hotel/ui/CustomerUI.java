package hotel.ui;

import hotel.model.Customer;
import hotel.util.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerUI extends JFrame {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;

    public CustomerUI() {
        setTitle("Customer Registration");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton registerButton = new JButton("Register");
        add(registerButton);

        JLabel statusLabel = new JLabel();
        add(statusLabel);

        registerButton.addActionListener((ActionEvent e) -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                statusLabel.setText("Please fill all fields.");
            } else {
                boolean success = addCustomerToDatabase(name, email, password);
                if (success) {
                    statusLabel.setText("Registration successful!");
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                } else {
                    statusLabel.setText("Registration failed.");
                }
            }
        });
    }

    private boolean addCustomerToDatabase(String name, String email, String password) {
        String sql = "INSERT INTO customers (name, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, password);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding customer: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerUI().setVisible(true);
        });
    }
}
