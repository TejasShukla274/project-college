package hotel.ui;

import hotel.model.Customer;
import hotel.util.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CustomerLoginUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public CustomerLoginUI() {
        setTitle("Customer Login");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JLabel statusLabel = new JLabel();
        add(statusLabel);

        loginButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (authenticate(email, password)) {
                statusLabel.setText("Login successful!");
                // Proceed to next screen or operations here
            } else {
                statusLabel.setText("Invalid email or password.");
            }
        });
    }

    private boolean authenticate(String email, String password) {
        String sql = "SELECT * FROM customers WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if a matching customer was found

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error occurred.");
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CustomerLoginUI().setVisible(true);
        });
    }
}
