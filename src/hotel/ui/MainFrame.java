package hotel.ui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Hotel Management System");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, spacing
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // padding

        // Buttons
        JButton btnAddGuest = new JButton("Add Guest");
        JButton btnViewRooms = new JButton("View Rooms");
        JButton btnBookRoom = new JButton("Book Room");
        JButton btnViewBookings = new JButton("View Bookings");

        JButton btnExit = new JButton("Exit");
        btnAddGuest.addActionListener(e -> new AddGuestForm());

        btnBookRoom.addActionListener(e -> new BookRoomForm());
        btnViewBookings.addActionListener(e -> new hotel.ui.ViewBookingsFrame());
        // Add buttons to panel
        panel.add(btnAddGuest);
        panel.add(btnViewRooms);
        panel.add(btnBookRoom);
        panel.add(btnViewBookings);
        panel.add(btnExit);

        // Add panel to frame
        add(panel);

        // Action listeners
        btnExit.addActionListener(e -> System.exit(0));

        // TODO: Add listeners to open other windows/screens

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
