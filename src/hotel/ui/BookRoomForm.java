package hotel.ui;

import hotel.model.Booking;
import hotel.model.Guest;
import hotel.model.Room;
import hotel.util.DatabaseUtil;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookRoomForm extends JFrame {

    private JComboBox<Guest> guestCombo;
    private JComboBox<Room> roomCombo;

    public BookRoomForm() {
        setTitle("Book a Room");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));  // Increased row count for optional refresh buttons

        guestCombo = new JComboBox<>();
        roomCombo = new JComboBox<>();

        // Load initial data
        loadGuests();
        loadAvailableRooms();

        JTextField checkInField = new JTextField("2025-06-01");
        JTextField checkOutField = new JTextField("2025-06-05");

        JButton bookButton = new JButton("Book Room");

        // Optional: refresh buttons if you want manual refresh (can remove if not needed)
        JButton refreshGuestsButton = new JButton("Refresh Guests");
        refreshGuestsButton.addActionListener(e -> loadGuests());

        JButton refreshRoomsButton = new JButton("Refresh Rooms");
        refreshRoomsButton.addActionListener(e -> loadAvailableRooms());

        add(new JLabel("Select Guest:"));
        add(guestCombo);
        add(refreshGuestsButton); // Optional refresh button for guests
        add(new JLabel(""));      // Empty placeholder for layout

        add(new JLabel("Select Room:"));
        add(roomCombo);
        add(refreshRoomsButton);  // Optional refresh button for rooms
        add(new JLabel(""));      // Empty placeholder for layout

        add(new JLabel("Check-in Date (yyyy-mm-dd):"));
        add(checkInField);
        add(new JLabel("Check-out Date (yyyy-mm-dd):"));
        add(checkOutField);
        add(new JLabel(""));      // Empty label for spacing
        add(bookButton);

        bookButton.addActionListener(e -> {
            Guest selectedGuest = (Guest) guestCombo.getSelectedItem();
            Room selectedRoom = (Room) roomCombo.getSelectedItem();

            if (selectedGuest == null || selectedRoom == null) {
                JOptionPane.showMessageDialog(this, "Please select a guest and a room.");
                return;
            }

            try {
                Date checkIn = java.sql.Date.valueOf(checkInField.getText());
                Date checkOut = java.sql.Date.valueOf(checkOutField.getText());

                if (checkOut.before(checkIn)) {
                    JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.");
                    return;
                }

                double totalPrice = calculateTotalPrice(selectedRoom.getPrice(), checkIn, checkOut);

                Booking booking = new Booking();
                booking.setGuestId(selectedGuest.getId());
                booking.setRoomNumber(selectedRoom.getRoomNumber());
                booking.setCheckInDate(checkIn);
                booking.setCheckOutDate(checkOut);
                booking.setTotalPrice(totalPrice);

                if (addBookingToDatabase(booking)) {
                    JOptionPane.showMessageDialog(this, "Room booked successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to book room.");
                }

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-mm-dd.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    // Method to load guests from DB and update guest combo box
    private void loadGuests() {
        List<Guest> guests = getAllGuests();
        guestCombo.setModel(new DefaultComboBoxModel<>(guests.toArray(new Guest[0])));
    }

    // Method to load rooms from DB and update room combo box
    private void loadAvailableRooms() {
        List<Room> rooms = getAvailableRooms();
        roomCombo.setModel(new DefaultComboBoxModel<>(rooms.toArray(new Room[0])));
    }

    public void refreshGuests() {
        loadGuests();
    }

    public void refreshRooms() {
        loadAvailableRooms();
    }

    private List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();
        String sql = "SELECT * FROM guests";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                guests.add(new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guests;
    }

    private List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE is_available = TRUE";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("room_number"),
                        rs.getString("type"),
                        rs.getBoolean("is_available"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    private boolean addBookingToDatabase(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, room_number, check_in_date, check_out_date, total_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getGuestId());
            stmt.setInt(2, booking.getRoomNumber());
            stmt.setDate(3, new java.sql.Date(booking.getCheckInDate().getTime()));
            stmt.setDate(4, new java.sql.Date(booking.getCheckOutDate().getTime()));
            stmt.setDouble(5, booking.getTotalPrice());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private double calculateTotalPrice(double pricePerNight, Date checkIn, Date checkOut) {
        long diff = checkOut.getTime() - checkIn.getTime();
        int nights = (int) (diff / (1000 * 60 * 60 * 24));
        return nights * pricePerNight;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookRoomForm::new);
    }
}
