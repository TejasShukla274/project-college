package hotel.ui;

import hotel.model.Guest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGuestForm extends JFrame {

    private JTextField nameField, emailField, phoneField;

    public AddGuestForm() {
        setTitle("Add New Guest");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel nameLabel = new JLabel("Name:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel phoneLabel = new JLabel("Phone:");

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        JButton addButton = new JButton("Add Guest");

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; panel.add(nameLabel, gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(emailLabel, gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(phoneLabel, gbc);
        gbc.gridx = 1; panel.add(phoneField, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String phone = phoneField.getText().trim();

                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Just create a Guest object here, do NOT save it here
                Guest guest = new Guest(name, email, phone);

                // Notify or pass this guest object back to the caller or use an event to handle saving
                JOptionPane.showMessageDialog(null, "Guest info collected!\nYou can now save this guest from your main app.");

                dispose(); // close form
            }
        });

        setVisible(true);
    }
}
