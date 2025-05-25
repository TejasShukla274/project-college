package hotel.ui;

import hotel.model.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewBookingsFrame extends JFrame {

    public ViewBookingsFrame() {
        setTitle("View Bookings");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Booking ID", "Guest ID", "Room Number", "Check-In Date", "Check-Out Date"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        // Hardcoded sample bookings
        List<Booking> bookings = getSampleBookings();

        for (Booking b : bookings) {
            model.addRow(new Object[]{
                    b.getBookingId(),
                    b.getGuestId(),
                    b.getRoomNumber(),
                    b.getCheckInDate(),
                    b.getCheckOutDate()
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private List<Booking> getSampleBookings() {
        List<Booking> bookings = new ArrayList<>();

        // Example dates (java.util.Date)
        Date checkIn1 = new Date(2025 - 1900, 5 - 1, 10);  // May 10, 2025
        Date checkOut1 = new Date(2025 - 1900, 5 - 1, 15); // May 15, 2025

        Date checkIn2 = new Date(2025 - 1900, 6 - 1, 1);   // June 1, 2025
        Date checkOut2 = new Date(2025 - 1900, 6 - 1, 5);  // June 5, 2025

        bookings.add(new Booking(1, 101, 201, checkIn1, checkOut1));
        bookings.add(new Booking(2, 102, 305, checkIn2, checkOut2));

        return bookings;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewBookingsFrame());
    }
}
